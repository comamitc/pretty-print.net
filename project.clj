(def project-version "0.2.0")

(defproject pp project-version

  :description "A Clojure(Script) Pretty Printer"
  :url "http://pretty-print.net/"

  :jvm-opts ["-Xmx384m"]

  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.170"]
                 [cljfmt "0.5.3"]
                 [cljsjs/react "0.12.2-7"]
                 [cljs-ajax "0.3.10"]
                 [sablono "0.3.4"]
                 [secretary "1.2.1"]
                 [quiescent "0.1.4"]]

  :plugins [[lein-cljsbuild "1.1.3"]
            [lein-npm "0.6.2"]
            [lein-less "1.7.5"]
            [lein-figwheel "0.5.4-7"]
            [lein-asset-minifier "0.3.0"]
            [lein-pdo "0.1.1"]]

  :source-paths ["src"]

  :npm {:dependencies [[source-map-support "0.4.0"]
                       [ws "0.8.1"]
                       [express "4.10.7"]
                       [body-parser "1.10.1"]
                       [js-beautify "1.5.4"]
                       [moment "2.9.0"]
                       [serve-static "1.10.2"]]}

  :less {:source-paths ["less"]
         :target-path  "public/css"}

  :figwheel {:css-dirs ["public/css"]
             :repl false}

  :minify-assets {:assets {"public/css/main.min.css"
                           "public/css/main.css"}
                  :options {:optimizations :advanced}}

  :clean-targets ^{:protect false} ["target"
                                    "public/out"
                                    "public/out-min"
                                    "public/js/pretty-print.js"
                                    "public/js/pretty-print.min.js"]

  :cljsbuild {:builds {:client {:source-paths ["src/pp/client"]
                                :figwheel     true
                                :compiler {:main pp.client.core
                                           :output-to "public/js/pretty-print.js"
                                           :output-dir "public/js/out"
                                           :closure-defines {pp.client.data/app-v ~project-version}
                                           :asset-path "js/out"}}

                       :client-min {:source-paths ["src/pp/client"]
                                    :compiler {:main pp.client.core
                                               :output-to "public/js/pretty-print.min.js"
                                               :output-dir "public/js/out-min"
                                               :optimizations :advanced
                                               :closure-defines {pp.client.data/app-v ~project-version}
                                               :pretty-print false}}
                       :server {:source-paths ["src/pp/server"]
                                :figwheel true
                                :compiler {:main pp.server.main
                                           :output-to "target/server/index.js"
                                           :output-dir "target/server"
                                           :target :nodejs
                                           :optimizations :none
                                           :source-map true}}}}
  :aliases {"dev" ["pdo" ["less" "auto"]
                   ["figwheel" "client" "server"]]
            "prod" ["do"  ["clean"]
                    ["less" "once"]
                    ["minify-assets"]
                    ["cljsbuild" "once" "client-min" "server"]]})
