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

(defroute "/" [] (main-page))
;
; (defroute "/about" [] (about-init!))

;; TODO: make a 404 page
(defroute "*" [] "Page Not Found")

;; listen to the hashchange event
(defn init []
  (let [h (History.)]
    (goog.events/listen h EventType/NAVIGATE #(secretary/dispatch! (.-token %)))
    (doto h (.setEnabled true))))
