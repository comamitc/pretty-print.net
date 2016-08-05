(ns pp.server.main
  (:require [cljs.nodejs :as node]
            [pp.server.utils :as util]
            [pp.server.format.javascript-pp :as web]
            [pp.server.format.clj-pp :as fclj]))

(enable-console-print!)

(def env (aget (js->clj (.-env node/process)) "NODE_ENV"))

(print env)

(def http (node/require "http"))
(def express (node/require "express"))
(def body-parser (node/require "body-parser"))
(def serve-static (node/require "serve-static"))
(def app (express))

(def config
  (util/json-parse (node/require "../../config/config.json")))

(def typefns {"js"   web/format-js
              "json" web/format-js
              "css"  web/format-css
              "html" web/format-html
              "clj"  fclj/format-clj
              "cljs" fclj/format-clj
              "edn"  fclj/format-clj})

(.use app (.json body-parser))
(.use app (.urlencoded body-parser #js {:extended true}))

;; routes
(. app (post "/node/format/:tipe"
             (fn [req res]
               (let [params   (util/json-parse (.-params req))
                     body     (util/json-parse (.-body req))
                     tipe     (:tipe params)
                     input    (:input body)
                     settings (or (:settings body) {})]
                 (try
                   (let [result ((get typefns tipe) input settings tipe)]
                     (-> res
                         (.status 200)
                         (.json (str result))))
                   (catch :default e
                     ;; TODO: figure out how to parse these errors!
                     (do
                       (.send (.status res 400) e))))))))

(. app (get "/node/format/ping"
            (fn [req res]
              (.send res "ok"))))

(when (not= env "production")
  (. app (use (serve-static "public" #js {:index "index.html"}))))

(def -main
  (fn []
      (doto (.createServer http #(app %1 %2))
            (.listen (:cljs-server-port config)))))

(set! *main-cli-fn* -main)
