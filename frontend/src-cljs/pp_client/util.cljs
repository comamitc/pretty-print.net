(ns pp-client.util
  (:require [jayq.core]))

;;------------------------------------------------------------------------------
;; Util Functions
;;------------------------------------------------------------------------------

(defn get-hash
  []
  (or (get (clojure.string/split (.-hash js/location) "/") 1) nil))

(defn set-hash
  [new-hash]
  (set! (.-hash js/location) new-hash))

(defn log
  "Log a Clojure thing."
  [thing]
  (js/console.log (pr-str thing)))

(defn js-log
  "Log a JavaScript thing."
  [thing]
  (js/console.log thing))

(defn uuid []
  "Create a UUID."
  []
  (apply
   str
   (map
    (fn [x]
      (if (= x \0)
        (.toString (bit-or (* 16 (.random js/Math)) 0) 16)
        x))
    "00000000-0000-4000-0000-000000000000")))

;;------------------------------------------------------------------------------
;; AJAX
;;------------------------------------------------------------------------------

(def default-ajax-options {
  :contentType "application/json; charset=UTF-8"
  :cache false
  :dataType "json"
  :error #(log "TODO: ajax error handling")})

;; TODO: use `:pre` to asset that opts has at a minimum a URL and success function
(defn doajax [opts]
  (let [opts2 (merge default-ajax-options opts)]
    (jayq.core/ajax opts2)))