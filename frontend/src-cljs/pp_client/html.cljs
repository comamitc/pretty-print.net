(ns pp-client.html
  (:require-macros [hiccups.core :as hiccups])
  (:require
    hiccups.runtime
    [pp-client.util :refer [js-log log]]))

;;------------------------------------------------------------------------------
;; HTML is super cool
;;------------------------------------------------------------------------------

;; TODO create a reference datastructure that can then be used to create this.
(hiccups/defhtml input-options []
  [:optgroup {:label "Clojure"}
    [:option {:value "clj"} "Clojure Code"]
    [:option {:value "edn"} "EDN"]]
  [:optgroup {:label "JavaScript"}
    [:option {:value "js"} "JavaScript Code"]
    [:option {:value "json"} "JSON"]])

(hiccups/defhtml header []
  [:header.header-outer-c5e7d
    [:div.header-inner-0f889
      [:div.left-9467a
        [:a.home-link-e4c1e {:href ""}
          "pretty-print"
          [:span.net-84e9a ".net"]]]
      [:div.right-1dd94
        [:select#inputSelect.big-select-51b29
          (input-options)]]
      [:div.clr-217e3]]])

(hiccups/defhtml right-body []
  [:div.right-body-caf9a
    [:button#formatBtn.btn-2d976 "Format"]])

(hiccups/defhtml left-body []
  [:div.left-body-ca07e
    [:textarea#mainTextarea.text-f3988]])

(hiccups/defhtml body [hash]
  [:div.body-outer-7cb5e
    [:div.body-inner-40af1
      [:h2.instructions-b15d3 (str "Paste " hash " below:")]
      (left-body)
      (right-body)
      [:div.clr-217e3]]])

(hiccups/defhtml footer []
  [:footer.ftr-outer-6bcd3
    [:div.ftr-inner-557c9
      "I am a footer."]])

(hiccups/defhtml page [hash]
  (header)
  (body hash)
  (footer))
