(ns pp-client.html
  (:require-macros [hiccups.core :as hiccups])
  (:require
    hiccups.runtime
    [pp-client.dom :refer [by-id]]
    [pp-client.util :refer [js-log log]]
    [quiescent :include-macros true]
    [sablono.core :as sablono :include-macros true]
    [secretary.core :as secretary]))

(def state (atom {:id "edn"
                  :desc "EDN"
                  :uri "/format/edn"}))

;;------------------------------------------------------------------------------
;; URLs
;;------------------------------------------------------------------------------

(defn- url [path]
  (if-let [base-href (:base-href config)]
    (str (replace base-href #"/$" "") path)
    path))

;;------------------------------------------------------------------------------
;; Event triggers
;;------------------------------------------------------------------------------ 
(defn- on-style-change [evt]
  (aset js/window "location" "hash" (str "/format/" evt.target.value)))

;;------------------------------------------------------------------------------
;; Footer
;;------------------------------------------------------------------------------

(def github-url "https://github.com/clojure/clojurescript")
(def issues-url "http://dev.clojure.org/jira/browse/CLJS")
(def mailing-list-url "http://groups.google.com/group/clojurescript")

(quiescent/defcomponent footer-docs-list []
  (sablono/html
  [:div.col-ace4b
    [:h5.hdr-856fa "Documentation"]
    [:ul
      [:li [:a.ftr-link-67c8e {:href (url "/getting-started")} "Getting Started"]]
      [:li [:a.ftr-link-67c8e {:href (url "/tutorials")} "Tutorials"]]
      [:li [:a.ftr-link-67c8e {:href (url "/docs")} "Docs"]]
      [:li [:a.ftr-link-67c8e {:href (url "/cheatsheet")} "Cheatsheet"]]]]))

(quiescent/defcomponent footer-learn-list []
  (sablono/html
  [:div.col-ace4b
    [:h5.hdr-856fa "Learn"]
    [:ul
      [:li [:a.ftr-link-67c8e {:href (url "/rationale")} "Rationale"]]
      [:li [:a.ftr-link-67c8e {:href (url "/faq")} "FAQ"]]]]))

(quiescent/defcomponent footer-community-list []
  (sablono/html
  [:div.col-ace4b
    [:h5.hdr-856fa "Community"]
    [:ul
      [:li [:a.ftr-link-67c8e {:href mailing-list-url}
        "Mailing List" [:i.fa.fa-external-link]]]
      ;; TODO: either make this a link or figure out how to style it as a non-link
      [:li [:a.ftr-link-67c8e {:href "#"} "IRC: #clojurescript"]]]]))

(quiescent/defcomponent footer-contribute-list []
  (sablono/html
  [:div.col-ace4b
    [:h5.hdr-856fa "Contribute"]
    [:ul
      [:li [:a.ftr-link-67c8e {:href github-url}
        "GitHub" [:i.fa.fa-external-link]]]
      [:li [:a.ftr-link-67c8e {:href issues-url}
        "JIRA / Issues" [:i.fa.fa-external-link]]]]]))

(def cljsinfo-license-url "https://github.com/oakmac/cljs.info/blob/master/LICENSE.md")
(def clojurescript-license-url "https://github.com/clojure/clojurescript#license")

(quiescent/defcomponent footer-bottom []
  (sablono/html
  [:div.bottom-31b43
    [:div.left-1764b
      [:p.small-14fbc
        "ClojureScript is released under the "
        [:a {:href clojurescript-license-url} "Eclipse Public License 1.0"]
        " and is Copyright &copy; Rich Hickey."]
      [:p.small-14fbc
        "cljs.info is released under the "
        [:a {:href cljsinfo-license-url} "MIT License"] "."]]
    [:div.right-e461e
      [:a.ftr-home-link-2c3b4 {:href (url "/")} "cljs" [:span.ftr-info-a5716 ".info"]]]
    [:div.clr-43e49]]))

(quiescent/defcomponent footer []
  (sablono/html
  [:div.ftr-outer-6bcd3
    [:div.ftr-inner-557c9
      (footer-docs-list)
      (footer-learn-list)
      (footer-community-list)
      (footer-contribute-list)
      [:div.clr-43e49]
      (footer-bottom)]]))

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
      (FooterInner)]))

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
      (footer)]))

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

