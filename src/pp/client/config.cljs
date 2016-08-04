(ns pp.client.config)

(def style-map {"edn"
                  {:id "edn"
                   :desc "EDN"
                   :uri "/node/format/edn"
                   :settings {}}
                "clj"
                  {:id "clj"
                   :desc "Clojure Code"
                   :uri "/node/format/clj"
                   :settings {}}
                "json"
                  {:id "json"
                   :desc "JSON"
                   :uri "/node/format/json"
                   :settings {}}})

(def state (atom {:success? false :error? false :value ""}))
