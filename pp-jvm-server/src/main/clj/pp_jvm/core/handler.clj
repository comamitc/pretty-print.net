(ns pp-jvm.core.handler
  (:use ring.adapter.jetty)
  (:require [ring.middleware.refresh :refer [wrap-refresh]]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [cheshire.core :refer [parse-string]]
            [pp-jvm.core.fn-maps :refer [mapfn]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [clojure.tools.logging :as log]))

(def config (parse-string (slurp "../config/config.json") true))

(defn- decode-body! [req] (parse-string (slurp (:body req)) true))

(defroutes app-routes
  (POST "/data/format/:tipe" request
    (let [tipe (:tipe (:params request))
          input (:input (decode-body! request))]
      (log/debug "Formating to type " tipe " for input " input)
      (mapfn input tipe)))
  (route/not-found "Not Found"))

(run-jetty (wrap-refresh app-routes) {:port (:jvm-server-port config)})