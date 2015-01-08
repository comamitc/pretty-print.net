(ns pp-server.core.handler
  (:require [compojure.core :refer :all]
            [cheshire.core :refer [parse-string]]
            [compojure.route :as route]
            [pp-server.format.clojure-pp :refer [format-clj!]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defn- decode-body! [req]
  (parse-string (slurp (:body req)) true))

(defroutes app-routes
  (POST "/format/:tipe" request
    (let [tipe (:tipe (:params request))
          body (decode-body! request)]
      (format-clj! (:input body) tipe)))
  (route/not-found "Not Found"))

(def app (wrap-defaults app-routes
    ;; turn off anti-forgery crap for now
    (assoc-in site-defaults [:security :anti-forgery] false)))
