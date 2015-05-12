(defproject clanhr/ring-test-client "0.1.0"
  :description "Utilities for testing controllers on top of ring"
  :url "https://github.com/clanhr/ring-test-client"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0-beta2"]
                 [clanhr/reply "0.2.0"]
                 [ring/ring-core "1.3.2"]
                 [ring/ring-jetty-adapter "1.3.2"]
                 [ring/ring-json "0.3.1"]
                 [org.clojure/data.json "0.2.6"]
                 [ring-cors "0.1.7"]
                 [compojure "1.3.3"]
                 [ring/ring-mock "0.2.0"]])
