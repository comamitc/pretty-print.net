(defproject pp-node-server "0.1.0-SNAPSHOT"
  :description "pretty-printer server side for pretty-print.net"
  :url "http://pretty-print.net"
  :cljsbuild
    {:builds
      [ {:id "server"
         :source-paths ["src/"]
         :compiler {:main main
                    :output-to "target/server/index.js"
                    :output-dir "target/server"
                    :target :nodejs
                    :optimizations :none
                    :source-map true}}]}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.170"]]
  :plugins [[lein-cljsbuild "1.1.3"]])
