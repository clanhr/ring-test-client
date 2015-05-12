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
   (reply/ok))
  ([request]
   (reply/ok request)))

(defroutes test-routes
  (GET "/" [] replier)
  (POST "/post" request (replier request))
  (PUT "/put" request (replier request))
  (GET "/auth-get" request (replier request)))


(def app
  (-> (core/routes test-routes)
      (compojure.handler/api)))

(deftest http-get
  (is (= 200 (:status (client/http-get app "/")))))
