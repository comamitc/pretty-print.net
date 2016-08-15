(ns pp.client.html
  (:require [rum.core :as rum]
            [pp.client.util :refer [js-log log]]
            [pp.client.dom :refer [by-id]]
            [pp.client.config :refer [state]]))

(log state)

(rum/defc page-contents []
  [:div#pageWrapper
    [:div.body-outer-7cb5e
      [:div.body-inner-40af1
        [:h2.instructions-b15d3 (str "Paste " (:desc state) ":")]
        [:div.clr-217e3]]]])

(defn main-page [style]
  (rum/mount (page-contents) (by-id "bodyWrapper")))
