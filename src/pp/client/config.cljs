(ns ^:figwheel-no-load pp.client.config
  (:require [pp.client.data :refer [get-localstorage]]
            [cljs.reader :refer [read-string]]))

(def style-map
  {:clj  "Clojure"
   :edn  "EDN"
   :json "JavaScript"})

(def browser-state
  {:history (or (read-string (get-localstorage :history)) ())})

(def state
 (atom
  (merge {:value    nil
           :history  ()
           :style    :edn
           :cm       nil
           :error?   false
           :success? false} browser-state)))
