(ns pp.client.data
  (:require
    [goog.crypt.base64 :as b64]))

(goog-define app-v "0.0.0")

(def id (b64/encodeString app-v))

(defn set-localstorage!
  "Set `key' in browser's localStorage to `val`."
  [key val]
  (.setItem (.-localStorage js/window) (str id key) val))

(defn get-localstorage
  "Returns value of `key' from browser's localStorage."
  [key]
  (.getItem (.-localStorage js/window) (str id key)))

(defn remove-item!
  "Remove the browser's localStorage value for the given `key`"
  [key]
  (.removeItem (.-localStorage js/window) (str id key)))
