(ns pp.client.html
  (:require [rum.core :as rum]
            [clojure.string :as s]
            [pp.client.util :refer [js-log log]]
            [pp.client.dom :refer [by-id]]
            [pp.client.config :refer [state style-map]]))

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


(rum/defc page-contents < rum/reactive []
  (let [*style (rum/cursor state :style)]
    [:div
      [:div.body-outer-7cb5e
        (nav-bar)
        [:div.container
          [:div.instructions-b15d3
            (str "Paste " (s/upper-case ((rum/react *style) style-map)) ":")]]]]))

(defn main-page []
  (rum/mount (page-contents) (.getElementById js/document "bodyWrapper")))
