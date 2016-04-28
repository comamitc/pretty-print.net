(ns pp-client.data
  (:require
    [ajax.core :refer [GET POST]]
    [pp-client.util :refer [js-log log]]))

(def id "9c9fb")

(defn format-input [state handler error-handler]
  (POST (:uri state) {:params {:input (:value state)
                               :settings (:settings state)} ;;(:settings @state-atom)}
                            :format :json
                            :keywords? true
                            :response-format :json
                            :handler handler
                            :error-handler error-handler}))

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
