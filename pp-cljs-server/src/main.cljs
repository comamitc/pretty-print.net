(ns main
  (:require [clojure.walk]
            [cljs.nodejs :as node]
            [utils :as util]
            [format.javascript-pp :refer [format-js]]))

(def http (node/require "http"))
(def express (node/require "express"))
(def app (express))

(def config
  (-> (node/require "./config.json")
    js->clj
    clojure.walk/keywordize-keys))

(def typefns {"js" format-js})

(defn- start-server []
  (doto app

    ;; routes
    (.post "/cljs/format/:tipe"
      (fn [req res] (.send res "hello world"))))

    ;; create the http server from the express app
    (let [http-server (.createServer http app)
          port (:port config)]
      ;; go time!
      (.listen http-server port)
      (util/tlog (str "CLJS-server listening on port " port))))

(defn -main [& args]
  (start-server))

(set! *main-cli-fn* -main)