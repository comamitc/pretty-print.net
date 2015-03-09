(ns pp-client.config)

(def default-clj-settings {:right-margin 
                              {:type "range"
                               :id "right-margin"
                               :name "Right Margin:"
                               :class "range-input-0b991" 
                               :min 20 
                               :max 120
                               :step 5
                               :value 80}
                           :miser-width 
                              {:type "range"
                               :id "miser-width"
                               :name "Miser Width:"
                               :class "range-input-0b991"  
                               :min 20 
                               :max 120
                               :step 5
                               :value 40}
                            :pretty
                              {:type "checkbox"
                               :id "pretty"
                               :name "Pretty?" 
                               :class "cmn-toggle cmn-toggle-round-flat"
                               :checked true}
                            :suppress-namespaces
                              {:type "checkbox"
                               :id "suppress-namespaces"
                               :name "Suppress Namespace?" 
                               :class "cmn-toggle cmn-toggle-round-flat"
                               :checked false}})

(def style-map {"edn"
                  {:id "edn"
                   :desc "EDN"
                   :uri "/jvm/format/edn"
                   :settings default-clj-settings}
                "clj"
                  {:id "clj"
                   :desc "Clojure Code"
                   :uri "/jvm/format/clj"
                   :settings default-clj-settings}
                "json"
                  {:id "json"
                   :desc "JSON"
                   :uri "/jvm/format/json"
                   :settings {}}})

(def state (atom {:success? false :error? false :value ""}))