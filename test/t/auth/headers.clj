(ns t.auth.headers
  (:use midje.sweet)
  (:require
    [clojure.string :as s]
    [clj-time.core :as t]
    [clj-time.format :as f]
    [org.httpkit.client :as http]))

(def example-key "wJalrXUtnFEMI/K7MDENG+bPxRfiCYEXAMPLEKEY")
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
(def suite-prefix (str (System/getenv "PWD") "/test/aws4_testsuite/"))

(defn decapitalize [string]
  (apply str
         (s/lower-case (first string))
         (rest string)))

(defn req->creq [method url params date host signature]
  (str method "\n"
       url "\n"
       params "\n"
       (decapitalize date) "\n"
       (decapitalize host) "\n\n"
       signature))

(defn parse-req [req]
  )

(fact "test 1"
  (->> (slurp (str suite-prefix "get-vanilla-query-order-key-case.req"))
       parse-req
       (apply req->creq)) => (slurp (str suite-prefix "get-vanilla-query-order-key-case.creq")))

(-> (slurp (str suite-prefix "get-vanilla-query-order-key-case.req"))
    s/split-lines
    println)

(println (slurp (str suite-prefix "get-vanilla-query-order-key-case.creq")))

;(with-open [resp (-> (java.net.URL. "https://www.google.com")
;                     .openStream
;                     java.io.InputStreamReader.
;                     java.io.BufferedReader.)]
;  (->> (line-seq resp)
;       (apply str)
;       ;println
;       ))
