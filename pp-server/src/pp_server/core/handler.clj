(ns pp-server.core.handler
  (:require [compojure.core :refer :all]
            [cheshire.core :refer [parse-string]]
            [compojure.route :as route]
            [pp-server.core.fn-maps :refer [mapfn]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defn- decode-body! [req]
  (parse-string (slurp (:body req)) true))

(defroutes app-routes
  (POST "/format/:tipe" request
    (let [tipe (:tipe (:params request))
          input (:input (decode-body! request))]
      (mapfn input tipe)))
  (route/not-found "Not Found"))

(def app (wrap-defaults app-routes
    ;; turn off anti-forgery crap for now
    (assoc-in site-defaults [:security :anti-forgery] false)))
