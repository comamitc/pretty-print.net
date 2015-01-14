(defproject pp-cljs-server "0.1.0-SNAPSHOT"
  :description "pretty-printer server side for pretty-print.net"
  :url "http://pretty-print.net"
  :cljsbuild
    {:builds
      [{:source-paths ["src"],
        :compiler
          {:optimizations :simple,
           :output-to "server.js",
           :target :nodejs,
           :pretty-print true}}]}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2665"]]
  :plugins [[lein-cljsbuild "1.0.4"]])
