(ns auth.test
  (:require
    [org.httpkit.client :as http]))

(defn http-post [uri options]
  (http/post
    uri options
    (fn [{:keys [status headers body error]}]
      {:error   error
       :status  status
       :headers headers
       :body    body})))

(defn http-get [uri options]
  (http/get
    uri options
    (fn [{:keys [status headers body error]}]
      {:error   error
       :status  status
       :headers headers
       :body    body})))

(defn sign [key msg])

(defn get-signature-key [key date-stamp region service]
  (-> (str "AWS4" key)
      (.encode "utf-8")
      (sign date-stamp)
      (sign region)
      (sign service)
      (sign "aws4_request")))