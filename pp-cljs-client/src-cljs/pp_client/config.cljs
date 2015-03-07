(ns pp-client.config)

(def style-map {"edn"
                 {:id "edn"
                  :desc "EDN"
                  :uri "/jvm/format/edn"
                  :settings {:right-margin 
                              {:type "range"
                               :id "right-margin"
                               :name "Right Margin" 
                               :min 20 
                               :max 120
                               :step 5
                               :value 80}}}
                "clj"
                 {:id "clj"
                  :desc "Clojure Code"
                  :uri "/jvm/format/clj"
                  :settings {}}})