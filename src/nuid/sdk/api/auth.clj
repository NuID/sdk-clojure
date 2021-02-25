(ns nuid.sdk.api.auth
  "The `auth` namespace provides an interface to simplify communicating with the
  [NuID Auth API](https://portal.nuid.io/docs/api). You will need an API Key
  from the [NuID Developer Portal](https://portal.nuid.io/guides/intro) in order
  to make authenticated calls to the Auth API.

  NOTE: You will need to configure this HTTP client with your API Key. See
  [[merge-opts!]] for configuration instructions."
  (:require
   [clj-http.client :as client]
   [clojure.string :as string]
   [jsonista.core :as json]))

(def default-opts
  "Default options for communicating with the NuID Auth API. Currently supported
  keywords are `::api-key` and `::host`."
  (atom
   {::api-key nil
    ::host    "https://auth.nuid.io"}))

(defn merge-opts!
  "Merge `opts` with [[default-opts]]. Currently only supported keywords are
  `::api-key` and `::host`.

  NOTE: You must set the `::api-key` or all HTTP requests will fail.

  ```clojure
  ;; during env/server setup
  (auth/merge-opts! {::auth/api-key \"my api key\"})

  ;; All handlers are now configured to talk to the API
  ;; with the correct Host and API Key
  (auth/credential-get some-nuid)
  ```"
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
  "Construct a URL from the given path and the `::host` set in
  [[default-opts]]."
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
  "Get a credential challenge from the API, usually during login flow. The
  returned challenge can be used to generate a proof from the user's secret.
  Used in conjunction with [[credential-get]] to retrieve the credential and
  [[challenge-verify]] to verify the challenge with the `proof` generated
  client-side. `credential` must conform to the `:nuid/credential` spec.

  The successful response body will contain the following string keys:

  + `\"nuid.credential.challenge/jwt\"` - JWT containing the challenge claims.

  Non-200 responses will throw an exception.

  ```clojure
  (try
    (let [user           (db/fetch-user :email email-address)
          credential-res (auth/credential-get (:nuid user))
          credential     (get-in credential-res [:body \"nuid/credential\"])
          challenge-res  (auth/challenge-get credential)
          challenge-jwt  (get-in challenge-res [:body \"nuid.credential.challenge/jwt\"])]
     ;; Return challenge-jwt to user so they can generate a proof
    )
    (catch Exception ex
      ;; challenge get failed))
  ```"
  [credential]
  (-post "/challenge" {:nuid/credential credential}))

(defn challenge-verify
  "Verify a credential challenge with a `proof` generated from the
  `challenge-jwt` claims and the user's secret. This proof is generated by
  calling `Zk.proofFromSecretAndChallenge(secret, challenge)` (available in the
  npm package `@nuid/zk`).

 `proof` must conform to the `:nuid.credential/proof` spec.

  The successful response body will by empty, if the response is 200 then the user
  has been authenticated succesfully and you can issue your user session.  `(= 200 (:status res))`

  Non-200 responses will throw an exception.

  ```clojure
  (try
    (auth/credential-create verified-credential)
    ;; ... issue session ...
    (catch Exception ex
    ;; verification failed))
  ````

  See [@nuid/zk](https://www.npmjs.com/package/@nuid/zk)
  See [@nuid/cli](https://www.npmjs.com/package/@nuid/cli)"
  [challenge-jwt proof]
  (-post
   "/challenge/verify"
   {:nuid.credential.challenge/jwt challenge-jwt
    :nuid.credential/proof         proof}))

(defn credential-create
  "Create a credential from a verified credential (meaning a credential
  generated from the user's secret), usually during user registration. The
  response body contains the new Credential and the user's unique NuID which
  should be used as a reference to the user's credential for later
  authentication attempts.

  Credential must conform to the `:nuid.credential/verified` spec and can be
  obtained by calling `Zk.verifiableFromSecret(secret)` (available in the npm
  package `@nuid/zk`).

  The successful response body will contain the following string keys:

  + `\"nu/id\"` - The unique NuID representing the credential.
  + `\"nuid/credential\"` - The newly minted credential.

  Non-200 responses will throw an exception.

  ```clojure
  (try
    (let [res        (auth/credential-create verified-credential)
          nuid       (get-in res [:body \"nu/id\"])
          credential (get-in res [:body \"nuid/credential\"])]
    ;; store nuid along with user record
    )
    (catch Exception ex
      ;; credential create failed))
  ```

  See [@nuid/zk](https://www.npmjs.com/package/@nuid/zk)
  See [@nuid/cli](https://www.npmjs.com/package/@nuid/cli)"
  [verified-credential]
  (-post "/credential" {:nuid.credential/verified verified-credential}))

(defn credential-get
  "Fetch a credential by it's unique `nuid` which can be extracted from the
  response to [[credential-create]].

  The successful response body will contain the following string keys:

  + `\"nuid/credential\"` - The fetched credential.

  Non-200 responses will throw an exception.

  ```clojure
  (try
    (let [user       (db/fetch-user :email email-address)
          res        (auth/credential-get (:nuid user))
          credential (get-in res [:body \"nuid/credential\"])]
    ;; fetch a credential challenge perhaps?
    )
    (catch Exception ex
      ;; credential create failed))
  ```

  Generally you will end up storing the NuID with your user record during
  registration. Later during login use the NuID to fetch the credential using
  this method, passing the returned credential from the response body to
  [[challenge-get]]. "
  "Retrieve a stored credential. Credential will conform to the
  `:nuid/credential` spec."
  [nuid]
  (-get (str "/credential/" nuid)))
