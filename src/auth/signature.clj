(ns auth.signature
  (:import
    javax.crypto.Mac
    javax.crypto.spec.SecretKeySpec))

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