(ns pp-client.html
  (:require
    [pp-client.dom :refer [by-id]]
    [pp-client.util :refer [js-log log]]
    [quiescent :include-macros true]
    [sablono.core :as sablono :include-macros true]))

;;------------------------------------------------------------------------------
;; HTML is super cool
;;------------------------------------------------------------------------------

(quiescent/defcomponent InputOptions []
  (sablono/html 
    [:select#inputSelect.big-select-51b29
      [:optgroup {:label "Clojure"}
        [:option {:value "clj"} "Clojure Code"]
        [:option {:value "edn"} "EDN"]
      [:optgroup {:label "JavaScript"}
        [:option {:value "js"} "JavaScript Code"]
        [:option {:value "json"} "JSON"]]]]))

(quiescent/defcomponent Header []
  (sablono/html 
    [:header.header-outer-c5e7d
      [:div.header-inner-0f889
        [:div.left-9467a
          [:a.home-link-e4c1e {:href ""}
            "pretty-print"
            [:span.net-84e9a ".net"]]]
        [:div.right-1dd94
          (InputOptions)]
        [:div.clr-217e3]]]))

(quiescent/defcomponent RightBody []
  (sablono/html 
    [:div.right-body-caf9a
      [:button#formatBtn.btn-2d976 "Format"]]))

(quiescent/defcomponent LeftBody []
  (sablono/html
    [:div.left-body-ca07e
      [:textarea#mainTextarea.text-f3988]]))

(quiescent/defcomponent Footer []
  (sablono/html
    [:footer.ftr-outer-6bcd3
      [:div.ftr-inner-557c9
        "I am a footer."]]))

(quiescent/defcomponent Body []
  (sablono/html
    [:div#pageWrapper
      (Header)
      [:div.body-outer-7cb5e
            [:div.body-inner-40af1
              [:h2.instructions-b15d3 (str "Paste EDN below:")]
              (LeftBody)
              (RightBody)
              [:div.clr-217e3]]]
      (Footer)]))

(defn init! []
  (quiescent/render (Body) (by-id "bodyWrapper")))
