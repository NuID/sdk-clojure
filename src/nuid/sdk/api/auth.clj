(ns nuid.sdk.api.auth
  (:require
   [clj-http.client :as client]
   [clojure.string :as string]
   [jsonista.core :as json]))

(def default-opts
  "Default options for communicating with the NuID Auth API."
  (atom
   {::api-key nil
    ::host    "https://auth.nuid.io"}))

(defn merge-opts!
  "Merge `opts` with [[default-opts]]. Currently only supported keywords are
  `::api-key` and `::host`."
  [opts]
  (swap! default-opts merge opts)
  (doall
   (for [[k v] @default-opts]
     (assert
      (not (string/blank? v))
      (str "Opt value " k " is blank")))))

(defn -parse-body
  [body]
  (if (string/blank? body)
    ""
    (json/read-value body)))

(defn -url
  "Construct a URL from the given path and the `::host` set in [[default-opts]]."
  [path]
  (str (::host @default-opts) path))

(defn -get
  "Perform a GET request against the Auth API."
  [path]
  (-> (-url path)
      (client/get
       {:accept  :json
        :headers {"X-API-Key" (::api-key @default-opts)}})
      (update :body -parse-body)))

(defn -post
  "Perform a POST request against the Auth API."
  [path data]
  (-> (-url path)
      (client/post
       {:accept       :json
        :body         (json/write-value-as-string data)
        :content-type :json
        :headers      {"X-API-Key" (::api-key @default-opts)}})
      (update :body -parse-body)))

(defn challenge-get
  "Get a challenge for the given credential. Credential must conform to the `:nuid/credential` spec."
  [credential]
  (-post "/challenge" {:nuid/credential credential}))

(defn challenge-verify
  "Verify the proof with the given challenge. Proof must conform to the `:nuid.credential/proof` spec."
  [challenge-jwt proof]
  (-post
   "/challenge/verify"
   {:nuid.credential.challenge/jwt challenge-jwt
    :nuid.credential/proof         proof}))

(defn credential-create
  "Create a new credential. Credential must conform to the `:nuid.credential/verified` spec."
  [verified-credential]
  (-post "/credential" {:nuid.credential/verified verified-credential}))

(defn credential-get
  "Retrieve a stored credential. Credential will conform to the `:nuid/credential` spec."
  [nuid]
  (-get (str "/credential/" nuid)))
