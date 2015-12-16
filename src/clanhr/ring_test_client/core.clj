(ns clanhr.ring-test-client.core
  "Main test utilities"
  (:require [ring.util.codec :as codec]
            [clanhr.reply.json :as json])
  (:use clojure.test
        ring.mock.request))

(defn http-plain-get
  "Creates a GET request. Translates edn -> json -> edn"
  ([app path]
   (app (request :get path)))
  ([app path data hh]
   (app (-> (request :get path)
            (header (first hh) (second hh))))))

(defn http-get
  "Creates a GET request. Translates edn -> json -> edn"
  ([app path]
   (let [response (app (request :get path))]
     (assoc response :body (json/build (:body response)))))
  ([app path data hh]
   (let [response (app (-> (request :get path)
                           (header (first hh) (second hh))))]
     (assoc response :body (json/build (:body response))))))

(defn post
  "Creates a POST request. Translates edn -> json -> edn"
  ([app path]
   (post app path {}))
  ([app path data]
    (let [response (app (request :post path (json/dump data)))]
      (assoc response :body (json/build (:body response))))))

(defn put
  "Creates a PUT request. Translates edn -> json -> edn"
  ([app path]
   (put app path {}))
  ([app path data]
    (let [response (app (request :put path (json/dump data)))]
      (println response)
      (assoc response :body (json/build (:body response))))))

(defn auth-put
  "Creates a PUT request authed for the given user. Translates edn -> json -> edn"
  ([app user path]
   (auth-put app user path {}))
  ([app user path data]
   (let [request (-> (request :put path (json/dump data))
                     (assoc-in [:headers "x-clanhr-auth-token"] (:token user)))
         response (app request)]
     (assoc response :body (json/build (:body response))))))

(defn auth-post
  "Creates a POST request authed for the given user. Translates edn -> json -> edn"
  ([app user path]
   (auth-post app user path {}))
  ([app user path data]
   (let [request (-> (request :post path (json/dump data))
                     (assoc-in [:headers "x-clanhr-auth-token"] (:token user)))
         response (app request)]
     (assoc response :body (json/build (:body response))))))

(defn auth-get
  "Creates a GET request authed for the given user. Translates edn -> json -> edn"
  [app user path]
  (let [request (-> (request :get path)
                    (assoc-in [:headers "x-clanhr-auth-token"] (:token user)))
        response (app request)]
    (assoc response :body (json/build (:body response)))))

(defn auth-plain-get
  "Creates a GET request authed for the given user."
  [app user path]
  (let [request (-> (request :get path)
                    (assoc-in [:headers "x-clanhr-auth-token"] (:token user)))
        response (app request)]
    response))
