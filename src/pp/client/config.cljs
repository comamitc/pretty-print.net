(ns ^:figwheel-no-load pp.client.config)

(def style-map
  {:clj  "Clojure"
   :edn  "EDN"
   :json "JavaScript"})

(def state
 (atom
  {:value    nil
   :history  []
   :style    :edn
   :cm       nil
   :error?   false
   :success? false}))
