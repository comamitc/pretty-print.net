(ns pp-client.dom
  (:require
    [pp-client.util :as util]))

;;------------------------------------------------------------------------------
;; Some Native DOM Helper Functions
;;------------------------------------------------------------------------------

(defn by-id [id]
  (.getElementById js/document id))

(defn get-value [id]
  (aget (by-id id) "value"))

(defn set-html! [id html]
  (aset (by-id id) "innerHTML" html))

(defn- set-value! [id v]
  (aset (by-id id) "value" v))

(defn show-el! [id]
  (aset (by-id id) "style" "display" ""))

(defn hide-el! [id]
  (aset (by-id id) "style" "display" "none"))
