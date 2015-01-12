(ns pp-server.core.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [cheshire.core :refer [parse-string]]
            [pp-server.core.fn-maps :refer [mapfn]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [clojure.tools.logging :as log]))

(defn- decode-body! [req]
  (parse-string (slurp (:body req)) true))

(defroutes app-routes
  (POST "/format/:tipe" request
    (let [tipe (:tipe (:params request))
          input (:input (decode-body! request))]
      (log/debug "Formating " input " to type " tipe)
      (mapfn input tipe)))
  (route/not-found "Not Found"))

(def app (wrap-defaults app-routes
    ;; turn off anti-forgery crap for now
    (assoc-in site-defaults [:security :anti-forgery] false)))
