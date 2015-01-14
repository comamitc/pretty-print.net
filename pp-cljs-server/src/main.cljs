(ns main
  (:require [clojure.walk]
            [cljs.nodejs :as node]
            [utils :as util]
            [format.javascript-pp :refer [format-js]]))

(def http (node/require "http"))
(def express (node/require "express"))
(def body-parser (node/require "body-parser"))
(def app (express))

(def config
  (util/convert-json (node/require "./config.json")))

(def typefns {"js" format-js})

(defn- start-server []
  (doto app

    (.use (.json body-parser))
    (.use (.urlencoded body-parser (clj->js {:extended true})))

    ;; routes
    (.post "/cljs/format/:tipe"
      (fn [req res]
        (let [params   (util/convert-json (.-params req))
              body     (util/convert-json (.-body req))
              tipe     (:tipe params)
              input    (:input body)
              settings (or (:settings body) {})]
          (util/tlog req)
          ((get typefns tipe) input settings))))

    ;; not found
    ;;(.use (fn [req res] (.send (.status res 404) "Not Found")))
    )

    ;; create the http server from the express app
    (let [http-server (.createServer http app)
          port (:port config)]
      ;; go time!
      (.listen http-server port)
      (util/tlog (str "CLJS-server listening on port " port))))

(defn -main [& args]
  (start-server))

(set! *main-cli-fn* -main)