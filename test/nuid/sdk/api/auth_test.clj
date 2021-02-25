(ns nuid.sdk.api.auth-test
  (:require
   [clojure.java.shell :as shell]
   [clojure.string :as string]
   [clojure.test :as t]
   [dotenv :refer [env]]
   [jsonista.core :as json]
   [nuid.base64 :as base64]
   [nuid.sdk.api.auth :as auth]))

(defn zk
  [command & args]
  (->> (json/write-value-as-string args)
       (shell/sh "npx" "-q" "-p" "@nuid/cli" "nuid-cli" "zk" command)
       (:out)
       (json/read-value)))

(defn assert-res
  [status res]
  (t/is (= status (:status res)))
  res)

(defn jwt-claims
  [jwt]
  (-> (string/split jwt #"\.")
      (second)
      (base64/decode)
      (json/read-value)))

(t/deftest ^:integration auth-api-integration-test
  (auth/merge-opts! {::auth/api-key (env "NUID_AUTH_API_KEY")
                     ::auth/host    (env "NUID_AUTH_API_HOST")})
  (let [secret              "my super secret password"
        verified-credential (zk "verifiableFromSecret" secret)

        created-res    (assert-res 201 (auth/credential-create verified-credential))
        nuid           (get-in created-res [:body "nu/id"])
        new-credential (get-in created-res [:body "nuid/credential"])
        _              (t/is (not (string/blank? nuid)))
        _              (t/is (not (nil? new-credential)))

        credential-res (assert-res 200 (auth/credential-get nuid))
        credential     (get-in credential-res [:body "nuid/credential"])
        _              (t/is (= (get new-credential "nuid.elliptic.curve/point")
                                (get credential "nuid.elliptic.curve/point")))

        challenge-res (assert-res 201 (auth/challenge-get credential))
        challenge-jwt (get-in challenge-res [:body "nuid.credential.challenge/jwt"])
        _             (t/is (not (string/blank? challenge-jwt)))

        challenge (jwt-claims challenge-jwt)
        proof     (zk "proofFromSecretAndChallenge" secret challenge)
        _         (assert-res 200 (auth/challenge-verify challenge-jwt proof))]
    (println "Huzzah, Magic Powers!")))
