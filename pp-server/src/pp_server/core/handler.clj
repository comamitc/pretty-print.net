(ns pp-server.core.handler
  (:require [compojure.core :refer :all]
            [cheshire.core :refer [parse-string]]
            [compojure.route :as route]
            [pp-server.format.clojure-pp :refer [format-clj!]]
            [pp-server.format.js-pp :refer [format-js!]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(def typefn 
  {"edn"      format-clj!
   "clojure"  format-clj!
   "json"     format-js!  })

(defn- decode-body! [req]
  (parse-string (slurp (:body req)) true))

(defroutes app-routes
  (POST "/format/:tipe" request
    (let [tipe (:tipe (:params request))
          body (decode-body! request)]
      ((get typefn tipe) (:input body) tipe)))
  (route/not-found "Not Found"))

(def app (wrap-defaults app-routes
    ;; turn off anti-forgery crap for now
    (assoc-in site-defaults [:security :anti-forgery] false)))
