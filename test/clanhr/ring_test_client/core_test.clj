(ns clanhr.ring-test-client.core-test
  "Main test utilities"
  (:use clojure.test
        compojure.core)
  (:require [clojure.stacktrace]
            [clanhr.ring-test-client.core :as client]
            [compojure.handler :as handler]
            [clanhr.reply.core :as reply]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.params :as ring-params]
            [ring.middleware.cors :as cors]
            [compojure.core :as core]
            [compojure.route :as route]))

(defn- replier
  "Replies with ok"
  ([]
   (reply/ok {:success true}))
  ([request]
   (reply/ok {:success true})))

(defroutes test-routes
  (GET "/" [] (replier))
  (POST "/post" request (replier request))
  (PUT "/put" request (replier request))
  (GET "/auth-get" request (replier request)))

(def app
  (-> (core/routes test-routes)
      (compojure.handler/api)))

(def user {:token 1})

(deftest http-plain-get
  (is (= 200 (:status (client/http-plain-get app "/")))))

(deftest http-get
  (is (= 200 (:status (client/http-get app "/")))))

(deftest http-auth-get
  (is (= 200 (:status (client/auth-get app user "/")))))

(deftest http-post
  (is (= 200 (:status (client/post app "/post")))))

(deftest http-auth-post
  (is (= 200 (:status (client/auth-post app user "/post")))))

(deftest http-put
  (is (= 200 (:status (client/put app "/put")))))

(deftest http-auth-put
  (is (= 200 (:status (client/auth-put app user "/put")))))

