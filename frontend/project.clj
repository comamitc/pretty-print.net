(defproject pp-client "0.1.0-SNAPSHOT"

  :description "FIXME: write this!"
  :url "http://example.com/FIXME"

  :dependencies [
    [org.clojure/clojure "1.6.0"]
    [org.clojure/clojurescript "0.0-2665"]
    [hiccups "0.3.0"]
    [jayq "2.5.4"]]

  :plugins [[lein-cljsbuild "1.0.4"]]

  :source-paths ["src"]

  :clean-targets ["target" "public/js/main.js" "public/js/main.min.js"]

  :cljsbuild {
    :builds {
      :main {
        :source-paths ["src-cljs"]
        :compiler {
          :output-to "public/js/main.js"
          :optimizations :whitespace }}

      :main-min {
        :source-paths ["src-cljs"]
        :compiler {
          :externs ["externs/jquery-1.9.js"]
          :output-to "public/js/main.min.js"
          :optimizations :advanced
          :pretty-print false }}}})
