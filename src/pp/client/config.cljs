(ns pp.client.config)

(def style-map
  {:clj  "Clojure"
   :edn  "EDN"
   :json "JavaScript"})

(def state (atom {:success? false :error? false :value nil :style :edn :cm nil}))
