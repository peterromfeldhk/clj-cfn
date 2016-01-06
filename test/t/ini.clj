(ns t.ini
  (:use midje.sweet)
  (:require
    [clojure.string :as s]
    [camel-snake-kebab.core :refer [->kebab-case-keyword]]))

(def t-ini
  '("[default]"
     "aws_access_key_id = access"
     "aws_secret_access_key = secret"
     "[stg]"
     "aws_access_key_id = stg-access"
     "aws_secret_access_key = stg-secret"))
(def r-ini
  {:default {:aws-access-key-id     "access"
             :aws-secret-access-key "secret"}
   :stg     {:aws-access-key-id     "stg-access"
             :aws-secret-access-key "stg-secret"}})

(defn read-ini [file]
  (with-open [rdr (clojure.java.io/reader file)]
    (->> (line-seq rdr)
         (remove (partial re-matches #"^\s*#.*$"))
         (remove empty?)
         doall)))

(defn parse-item [item]
  (->> (s/split item #"=")
       (mapv s/trim)))

(defn parse-ini [ini]
  (let [section (atom :default)]
    (reduce
      (fn [ret item]
        (if (vector? (read-string item))
          (do
            (reset! section (-> item read-string first ->kebab-case-keyword))
            ret)
          (let [[k v] (parse-item item)]
            (assoc-in ret [@section (->kebab-case-keyword k)] (str v)))))
      {} ini)))

(fact "read ini"
  (read-ini "test/t/example.ini") => t-ini)

(fact "parse ini line-seq"
  (parse-ini t-ini) => r-ini)

