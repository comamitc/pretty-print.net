(ns pp.client.config)

(def style-map
  {:clj "Clojure Code"
   :edn "EDN"
   :json "JSON"})

(def state (atom {:success? false :error? false :value "" :style :edn}))
