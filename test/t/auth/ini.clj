(ns t.auth..ini
  (:use midje.sweet)
  (:require
    [auth.ini :refer :all]))

(def r-ini
  '("[default]"
     "aws_access_key_id = access"
     "aws_secret_access_key = secret"
     "[stg]"
     "aws_access_key_id = stg-access"
     "aws_secret_access_key = stg-secret"))
(def p-ini
  {:default {:aws-access-key-id     "access"
             :aws-secret-access-key "secret"}
   :stg     {:aws-access-key-id     "stg-access"
             :aws-secret-access-key "stg-secret"}})

(fact "read ini"
  (read-ini "test/t/example.ini") => r-ini)

(fact "parse ini line-seq"
  (parse-ini r-ini) => p-ini)


