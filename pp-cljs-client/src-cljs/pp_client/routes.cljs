(ns pp-client.routes
  (:require
      [goog.events :as events]
      [goog.history.EventType :as EventType]
      [secretary.core :as secretary :refer-macros [defroute]]
      [pp-client.html :as html]
      [pp-client.util :refer [js-log log]]
      [pp-client.config :refer [style-map]])
  (:import goog.History))

(secretary/set-config! :prefix "#")

(defn- set-default-uri
  [style]
  (js-log "found invalid style ")
  (js-log style)
  (aset js/window "location" "hash" "/format/edn"))

(defroute "/format/:style" [style]
  (let [norm-style (.toLowerCase style)
        valid-style? (contains? style-map norm-style)]
    (if valid-style?
      (html/init! (get style-map norm-style))
      (set-default-uri norm-style))))

(defroute "/about" [] (html/about-init!))

;; TODO: make a 404 page
(defroute "*" []
  (set-default-uri ""))

;; listen to the hashchange event
(defn init []
  (let [h (History.)]
    (goog.events/listen h EventType/NAVIGATE #(secretary/dispatch! (.-token %)))
    (doto h (.setEnabled true))))