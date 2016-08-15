(ns pp.client.routes
  (:require
      [goog.events :as events]
      [goog.history.EventType :as EventType]
      [secretary.core :as secretary :refer-macros [defroute]]
      [cljs.reader :refer [read-string]]
      [pp.client.html :refer [main-page]]
      ; [pp.client.html.main-page :refer [main-init!]]
      ; [pp.client.html.about-page :refer [about-init!]]
      [pp.client.data :refer [get-localstorage]]
      [pp.client.util :refer [js-log log]]
      [pp.client.config :refer [style-map]])
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
      ;; then
      (let [custom-preset (get-localstorage norm-style)
            style (get style-map norm-style)]
        (if (or (some? custom-preset) (not (empty? custom-preset)))
          ;; then
          (main-page (assoc style :settings (read-string custom-preset)))
          ;; else
          (main-page style)))
      ;; else
      (set-default-uri norm-style))))
;
; (defroute "/about" [] (about-init!))

;; TODO: make a 404 page
(defroute "*" []
  (set-default-uri ""))

;; listen to the hashchange event
(defn init []
  (let [h (History.)]
    (goog.events/listen h EventType/NAVIGATE #(secretary/dispatch! (.-token %)))
    (doto h (.setEnabled true))))
