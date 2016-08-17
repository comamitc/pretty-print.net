(ns pp.client.config)

(def style-map
  {:clj "Clojure"
   :json "JavaScript"})

(def state (atom {:success? false :error? false :value nil :style :clj :cm nil}))
