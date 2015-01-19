(ns main
  (:require [cljs.nodejs :as node]
            [utils :as util]
            [format.javascript-pp :as web]))

(def http (node/require "http"))
(def express (node/require "express"))
(def body-parser (node/require "body-parser"))
(def app (express))

(def config
  (util/json-parse (node/require "../config/config.json")))

(def typefns {"js"   web/format-js
              "css"  web/format-css
              "html" web/format-html})

(defn- start-server []
  (doto app

    (.use (.json body-parser))
    (.use (.urlencoded body-parser (clj->js {:extended true})))

    ;; routes
    (.post "/cljs/format/:tipe"
      (fn [req res]
        (let [params   (util/json-parse (.-params req))
              body     (util/json-parse (.-body req))
              tipe     (:tipe params)
              input    (:input body)
              settings (or (:settings body) {})]
          (.send (.status res 200) ((get typefns tipe) input settings)))))

    ;; not found
    (.use (fn [req res] (.send (.status res 404) "Not Found"))))

    ;; create the http server from the express app
    (let [http-server (.createServer http app)
          port (:cljs-server-port config)]
      ;; go time!
      (.listen http-server port)
      (util/tlog (str "CLJS-server listening on port " port))))

(defn -main [& args]
  (start-server))

(enable-console-print!)
(set! *main-cli-fn* -main)