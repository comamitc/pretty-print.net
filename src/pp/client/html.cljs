(ns pp.client.html
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [rum.core :as rum]
            [clojure.string :as s]
            [pp.client.util :refer [js-log log]]
            [pp.client.dom :refer [by-id]]
            [pp.client.config :refer [state style-map]]
            [cljsjs.codemirror]
            [cljsjs.codemirror.mode.clojure]
            [cljsjs.codemirror.mode.javascript]))

(rum/defc nav-bar < rum/reactive []
  (let [*style (rum/cursor state :style)]
    [:div.header-0f889
      [:div.container
        [:a.home-link-e4c1e {:href "" :class "nine columns"} "pretty-print"
          [:span.net-84e9a ".net"]]
        [:select.big-select-51b29
          {:class "three columns"
           :default-value (name (rum/react *style))
           :on-change #(reset! *style (keyword (-> % .-target .-value)))}
          (for [kv style-map]
            [:option {:key   (first kv)
                      :value (name (first kv))}
              (second kv)])]]]))

(defn- handle-on-change [cm _]
  (let [*value (rum/cursor state :value)
        v (.getValue cm)]
    (reset! *value v)))

(def mode-map {:clj "clojure" :edn "clojure" :json "javascript"})

(defn style-update [st]
  (let [style (first (:rum/args st))
        cm    (atom (:cm st))]
    (when-not @cm
      (reset! cm (js/CodeMirror. (by-id "code-editor")
                                 #js {:lineNumbers true}))
      (.on @cm "change" handle-on-change))
    (.setOption @cm "mode" (style mode-map))
    (assoc st :cm @cm)))

(def code-editor-mixin
  {:did-update style-update
   :transfer-state (fn [old-state state]
                    (assoc state :cm (:cm old-state)))
   :did-mount style-update})


(rum/defc code-editor < code-editor-mixin [style]
  [:div.editor-40af1 {:id "code-editor"}])

(rum/defc page-contents < rum/reactive []
  (let [*style (rum/cursor state :style)]
    [:div
      [:div.body-outer-7cb5e
        (nav-bar)
        [:div.container
          [:div
            [:button.u-pull-right.btn-5a8ac.button-primary "Format"]
            [:div.instructions-b15d3
              (str "Paste " ((rum/react *style) style-map)) ":"]]
          (code-editor (rum/react *style))]]]))


(defn main-page []
  (rum/mount (page-contents) (.getElementById js/document "bodyWrapper")))
