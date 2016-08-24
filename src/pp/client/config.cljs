(ns ^:figwheel-no-load pp.client.config)

(def style-map
  {:clj       {:desc "Clojure"    :mode "clojure"}
   :edn       {:desc "EDN"        :mode "clojure"}
   :json      {:desc "JavaScript" :mode "javascript"}
   :json->edn {:desc "JSON->EDN"  :mode {:from "javascript" :to "clojure"}}
   :edn->json {:desc "EDN->JSON"  :mode {:from "clojure" :to "javascript"}}})

(def state (atom {:success? false :error? false :value nil :style :edn :cm nil}))
