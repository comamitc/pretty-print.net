(ns pp-server.core.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

;; TODO: eventually abstract the route to take a type
;; this will help use a map of functions to the type.
(defroutes app-routes
  (POST "/format/:type" request 
    request) ;; TODO: just echos request currently
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
