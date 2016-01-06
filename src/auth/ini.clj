(ns auth.ini
  (:require
    [clojure.string :as s]
    [camel-snake-kebab.core :refer [->kebab-case-keyword]]))

(defn read-ini [file]
  (with-open [rdr (clojure.java.io/reader file)]
    (->> (line-seq rdr)
         (remove (partial re-matches #"^\s*#.*$"))
         (remove empty?)
         doall)))

(defn parse-item [item]
  (->> (s/split item #"=")
       (map s/trim)))

(defn parse-ini [ini]
  (let [section (atom :default)]
    (reduce
      (fn [ret element]
        (if (vector? (read-string element))
          (do
            (reset! section (-> element read-string first ->kebab-case-keyword))
            ret)
          (let [[k v] (parse-item element)]
            (assoc-in ret [@section (->kebab-case-keyword k)] (str v)))))
      {} ini)))
