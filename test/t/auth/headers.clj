(ns t.auth.headers
  (:use midje.sweet)
  (:require
    [clj-time.core :as t]
    [clj-time.format :as f]
    [org.httpkit.client :as http]))

(def headers
  {"X-Amz-Algorithm"     "AWS4-HMAC-SHA256"
   "X-Amz-Credential"    "AKIDEXAMPLE/20150830/us-east-1/iam/aws4_request"
   "X-Amz-Date"          "20150830T123600Z"
   "X-Amz-SignedHeaders" "content-type;host;x-amz-date"})

(def params
  {:headers      headers
   :query-params {"Action"  "ListUsers"
                  "Version" "2010-05-08"}})

(def header-formatter (f/formatters :basic-date-time-no-ms))
(def component-formatter (f/formatters :basic-date))

(facts "is true"
  1 => 1)

(println "=========START=========\n")

(let [time (t/date-time 2015 5 8 12 36)]
  (println (f/unparse header-formatter time))
  (println (f/unparse component-formatter time)))

; lets see how easy we can get rid of http-kit dep
; even if i hate java + java docs!
(with-open [resp (-> (java.net.URL. "https://www.google.com")
                     .openStream
                     java.io.InputStreamReader.
                     java.io.BufferedReader.)]
  (->> (line-seq resp)
       (apply str)
       ;println
       ))


(println "\n==========END==========")
