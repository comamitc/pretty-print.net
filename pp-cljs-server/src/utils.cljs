(ns utils
  (:require [cljs.nodejs :as node]
            [clojure.walk]))

(def moment (node/require "moment"))

(defn now []
  (.format (moment) "DD MMM HH:mm:ss"))

(defn js-log [js-thing]
  (.log js/console js-thing))

;; tlog = "timestamp log"
;; just adds a timestamp in front of a string
(defn tlog [msg]
  (.log js/console (str (now) " - " msg)))

(defn convert-json [json]
  (-> json
      js->clj
      clojure.walk/keywordize-keys))