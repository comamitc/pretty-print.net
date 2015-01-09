(defproject pp-server "0.1.0-SNAPSHOT"
  :description "pretty-printer server side for pretty-print.net"
  :url "http://pretty-print.net"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [compojure "1.3.1"]
                 [ring/ring-defaults "0.1.2"]
                 [org.clojure/tools.reader "0.8.13"]
                 [cheshire "5.3.1"]
                 [org.clojure/data.json "0.2.5"]]
  :plugins [[lein-ring "0.8.13"]]
  :ring {:handler pp-server.core.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring-mock "0.1.5"]]}})
