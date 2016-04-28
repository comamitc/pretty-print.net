(ns main
  (:require [cljs.nodejs :as node]
            [utils :as util]
            [format.javascript-pp :as web]
            [format.clj-pp :as fclj]))

(enable-console-print!)

(def http (node/require "http"))
(def express (node/require "express"))
(def body-parser (node/require "body-parser"))
(def app (express))

(def config
  (util/json-parse (node/require "../config/config.json")))

(def typefns {"js"   web/format-js
              "json" web/format-js
              "css"  web/format-css
              "html" web/format-html
              "clj"  fclj/format-clj
              "cljs" fclj/format-clj
              "edn"  fclj/format-clj})

(defn- start-server []
  (doto app

    (.use (.json body-parser))
    (.use (.urlencoded body-parser #js {:extended true}))

    ;; routes
    (.post "/node/format/:tipe"
      (fn [req res]
        (let [params   (util/json-parse (.-params req))
              body     (util/json-parse (.-body req))
              tipe     (:tipe params)
              input    (:input body)
              settings (or (:settings body) {})]
          (try
            (let [result ((get typefns tipe) input settings)]
              (-> res
                  (.status 200)
                  (.json ((get typefns tipe) input settings))))
            (catch :default e
              ;; TODO: figure out how to parse these errors!
              (do
                (print e)
                (.send (.status res 500) e)))))))

    ;; not found
    (.use (fn [req res] (.send (.status res 404) "Not Found"))))

    ;; create the http server from the express app
  (let [http-server (.createServer http app)
        port (:cljs-server-port config)]
      ;; go time!
    (.listen http-server port)
    (util/tlog (str "NodeJS-server listening on port " port))))

(defn -main [& args]
  (start-server))

(set! *main-cli-fn* -main)
