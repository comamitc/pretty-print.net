(ns pp-server.core.handler
  (:require [compojure.core :refer :all]
            [cheshire.core :refer [parse-string]]
            [compojure.route :as route]
            [pp-server.format.clojure-pp :refer [format-clj!]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defn- decode-body! [req]
  (parse-string (slurp (:body req)) true))

;; TODO: eventually abstract the route to take a type
;; this will help use a map of functions to the type.
(defroutes app-routes
  (POST "/format/:dest" request
    ;;(println request)
    (let [dest (:dest (:params request))
          body (decode-body! request)]
      ;;(println (str "Destination: " dest))
      (format-clj! (:input body)))) ;; TODO: just echos request currently
  (route/not-found "Not Found"))

;; TODO: incorporate a configurable port number
(def app (wrap-defaults app-routes
    ;; turn off anti-forgery crap for now
    (assoc-in site-defaults [:security :anti-forgery] false)))
