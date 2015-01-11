(ns utils
  (:require [cljs.nodejs :as node]))

(def moment (node/require "moment"))

(defn now []
  (.format (moment) "DD MMM HH:mm:ss"))

(defn js-log [js-thing]
  (.log js/console js-thing))

;; tlog = "timestamp log"
;; just adds a timestamp in front of a string
(defn tlog [msg]
  (.log js/console (str (now) " - " msg)))