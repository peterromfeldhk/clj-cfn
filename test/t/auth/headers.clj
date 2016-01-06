(ns t.auth.headers
  (:use midje.sweet))

(def headers {"Action"              "ListUsers"
              "Version"             "2010-05-08"
              "X-Amz-Algorithm"     "AWS4-HMAC-SHA256"
              "X-Amz-Credential"    "AKIDEXAMPLE/20150830/us-east-1/iam/aws4_request"
              "X-Amz-Date"          "20150830T123600Z"
              "X-Amz-SignedHeaders" "content-type;host;x-amz-date"})

(def header-formatter :basic-date-time-no-ms)
(def component-formatter :basic-date)

(facts "is true"
  1 => 1)