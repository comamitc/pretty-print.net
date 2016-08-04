(defproject pp-client "0.1.0-SNAPSHOT"

  :description "FIXME: write this!"
  :url "http://example.com/FIXME"

  :dependencies [
                 [org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2985"]
                 [cljsjs/react "0.12.2-7"]
                 [cljs-ajax "0.3.10"]
                 [sablono "0.3.4"]
                 [secretary "1.2.1"]
                 [quiescent "0.1.4"]]

  :plugins [[lein-cljsbuild "1.0.4"]]

  :source-paths ["src"]

  :clean-targets ["target" "public/out" "public/out-min" "public/js/pretty-print.js" "public/js/pretty-print.min.js"]

  :jvm-opts ["-Xmx384m"]

  :cljsbuild {:builds {:main {:source-paths ["src-cljs"]}}
                 :compiler {:output-to "public/js/pretty-print.js"
                            :output-dir "public/out"
                            :optimizations :whitespace}

              :main-min {:source-paths ["src-cljs"]
                         :compiler {:output-to "public/js/pretty-print.min.js"
                                    :output-dir "public/out-min"
                                    :optimizations :advanced
                                    :pretty-print false}}})
