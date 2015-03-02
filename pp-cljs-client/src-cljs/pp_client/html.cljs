(ns pp-client.html
  (:require
    [pp-client.dom :refer [by-id]]
    [pp-client.util :refer [js-log log]]
    [quiescent :include-macros true]
    [sablono.core :as sablono :include-macros true]
    [secretary.core :as secretary]))

(def state (atom {:id "edn"
                  :desc "EDN"
                  :uri "/format/edn"}))

;;------------------------------------------------------------------------------
;; Event triggers
;;------------------------------------------------------------------------------ 
(defn- on-style-change [evt]
  (aset js/window "location" "hash" (str "/format/" evt.target.value)))

;;------------------------------------------------------------------------------
;; HTML is super cool
;;------------------------------------------------------------------------------

(quiescent/defcomponent InputOptions [state]
  (sablono/html 
    [:select#inputSelect.big-select-51b29 
      {:on-change #(on-style-change %1)}
      [:optgroup {:label "Clojure"}
        [:option {:value "clj"} "Clojure Code"]
        [:option {:value "edn"} "EDN"]
      [:optgroup {:label "JavaScript"}
        [:option {:value "js"} "JavaScript Code"]
        [:option {:value "json"} "JSON"]]]]))

(quiescent/defcomponent Header [state]
  (sablono/html 
    [:header.header-outer-c5e7d
      [:div.header-inner-0f889
        [:div.left-9467a
          [:a.home-link-e4c1e {:href ""}
            "pretty-print"
            [:span.net-84e9a ".net"]]]
        [:div.right-1dd94
          (InputOptions state)]
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

(quiescent/defcomponent Body [state]
  (sablono/html
    [:div#pageWrapper
      (Header state)
      [:div.body-outer-7cb5e
            [:div.body-inner-40af1
              [:h2.instructions-b15d3 (str "Paste " (:desc state) " below:")]
              (LeftBody)
              (RightBody)
              [:div.clr-217e3]]]
      (Footer)]))

;;------------------------------------------------------------------------------
;; State Change and Rendering
;;------------------------------------------------------------------------------

(def request-anim-frame (aget js/window "requestAnimationFrame"))
(def cancel-anim-frame (aget js/window "cancelAnimationFrame"))
(def anim-frame-id nil)

(defn- on-change-state [_kwd _the-atom _old-state new-state]
  ;; cancel any previous render functions if they happen before the next
  ;; animation frame
  (when anim-frame-id
    (cancel-anim-frame anim-frame-id)
    (set! anim-frame-id nil))

  ;; put the render function on the next animation frame
  (let [render-fn #(quiescent/render (Body new-state) (by-id "bodyWrapper"))]
    (set! anim-frame-id (request-anim-frame render-fn))))

(add-watch state :main on-change-state)

;;------------------------------------------------------------------------------
;; init
;;------------------------------------------------------------------------------

(defn init! [style]
  (reset! state style))

