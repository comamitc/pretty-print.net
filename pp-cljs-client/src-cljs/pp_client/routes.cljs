(ns pp-client.routes
  (:require
      [goog.events :as events]
      [goog.history.EventType :as EventType]
      [pp-client.html :as html]
      [secretary.core :as secretary :refer-macros [defroute]]
      [pp-client.util :refer [js-log log]])
  (:import goog.History))

(secretary/set-config! :prefix "#")
(def style-map {"edn"
                 {:id "edn"
                  :desc "EDN"
                  :uri "/jvm/format/edn"}
                "clj"
                 {:id "clj"
                  :desc "Clojure"
                  :uri "/jvm/format/clj"}})

(defn- set-default-uri
  []
  (js-log "found invalid style")
  (aset js/window "location" "hash" "/format/edn"))

(defroute "/format/:style" [style]
  (let [norm-style (.toLowerCase style)
        valid-style? (contains? style-map norm-style)]
    (if valid-style?
      (html/init! (get style-map norm-style))
      (set-default-uri))))

;; TODO: make a 404 page
(defroute "*" []
  (set-default-uri))

;; listen to the hashchange event
(defn init []
  (let [h (History.)]
    (goog.events/listen h EventType/NAVIGATE #(secretary/dispatch! (.-token %)))
    (doto h (.setEnabled true))))