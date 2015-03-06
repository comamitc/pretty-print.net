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
                  :uri "/jvm/format/edn"
                  :settings {:right-margin 
                              {:type "range"
                               :id "right-margin"
                               :name "Right Margin" 
                               :min 20 
                               :max 120
                               :step 5
                               :value 40}}}
                "clj"
                 {:id "clj"
                  :desc "Clojure Code"
                  :uri "/jvm/format/clj"}})

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