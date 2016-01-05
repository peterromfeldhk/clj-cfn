(ns t.auth
  (:use midje.sweet)
  (:import
    javax.crypto.Mac
    javax.crypto.spec.SecretKeySpec))

; refer to http://docs.aws.amazon.com/general/latest/gr/signature-v4-examples.html#signature-v4-examples-other
(def test-key "wJalrXUtnFEMI/K7MDENG+bPxRfiCYEXAMPLEKEY")
(def test-date "20120215")
(def test-region "us-east-1")
(def test-service "iam")
(def k-secret "41575334774a616c725855746e46454d492f4b374d44454e472b62507852666943594558414d504c454b4559")
(def k-date "969fbb94feb542b71ede6f87fe4d5fa29c789342b0f407474670f0c2489e0a0d")
(def k-region "69daa0209cd9c5ff5c8ced464a696fd4252e981430b10e3d3fd8e2f197d7a70c")
(def k-service "f72cfd46f26bc4643f06a11eabb6c0ba18780c19a8da0c31ace671265e3c87fa")
(def k-signing "f4780e2d9f65fa895f9c67b32ce1baf0b0d8a43505a000a1a9e090d414db404d")

(defn sign [key data]
  "Returns the signature of a string with a given key, using a SHA-256 HMAC."
  (let [hmac-key (SecretKeySpec. key "HmacSHA256")
        hmac (doto (Mac/getInstance "HmacSHA256") (.init hmac-key))]
    (.doFinal hmac (.getBytes data "UTF8"))))

(defn get-signature [key date-stamp region service]
  (-> (str "AWS4" key)
      (.getBytes "UTF8")
      (sign date-stamp)
      (sign region)
      (sign service)
      (sign "aws4_request")))

(defn to-hex-string [bytes]
  "Convert bytes to a String"
  (apply str (map #(format "%02x" %) bytes)))

(facts "aws signing"
  (let [step-1 (-> (str "AWS4" test-key)
                   (.getBytes "UTF8"))
        step-2 (sign step-1 test-date)
        step-3 (sign step-2 test-region)
        step-4 (sign step-3 test-service)
        final (get-signature test-key test-date test-region test-service)]
    (to-hex-string step-1) => k-secret
    (to-hex-string step-2) => k-date
    (to-hex-string step-3) => k-region
    (to-hex-string step-4) => k-service
    (to-hex-string final) => k-signing))

(println (get-signature test-key test-date test-region test-service))