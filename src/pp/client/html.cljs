(ns pp.client.html
  (:require [rum.core :as rum]
            [clojure.string :as s]
            [pp.client.util :refer [js-log log]]
            [pp.client.dom :refer [by-id]]
            [pp.client.config :refer [state style-map]]
            [pp.client.format :refer [format-input!]]
            [cljsjs.codemirror]
            [cljsjs.codemirror.mode.clojure]
            [cljsjs.codemirror.mode.javascript]))

;;------------------------------------------------------------------------------
;; Actions
;;------------------------------------------------------------------------------
(defn- handle-on-click [evt]
  (let [d-state @state
        *style (:style d-state)
        *value (:value d-state)
        *cm    (:cm d-state)
        out    (format-input! *style *value)]
     (if (contains? out :result)
       (.setValue *cm (:result out))
       (swap! state assoc :error? (:error out)))))

(defn- handle-on-change [cm _]
  (let [*cm    (:cm @state)
        v      (.getValue cm)]
    (when (nil? *cm)
      (swap! state assoc :cm cm))
    (swap! state assoc :value v :error? nil)))

(def mode-map {:clj "clojure" :edn "clojure" :json "javascript"})

(defn style-update [st]
  (let [style (first (:rum/args st))
        cm    (atom (:cm st))]
    (when-not @cm
      (reset! cm (js/CodeMirror. (by-id "code-editor")
                                 #js {:lineNumbers true}))
      (.on @cm "change" handle-on-change))
    (.setOption @cm "mode" (style mode-map))
    ;; stupid hack because args are being treated "weird"
    (assoc st :cm @cm)))

;;------------------------------------------------------------------------------
;; Code Editor
;;------------------------------------------------------------------------------
(def code-editor-mixin
  {:did-update style-update
   :transfer-state (fn [old-state state]
                    (assoc state :cm (:cm old-state)))
   :did-mount style-update})

(rum/defc code-editor < code-editor-mixin [style]
  [:div.editor-40af1 {:id "code-editor"}])

;;------------------------------------------------------------------------------
;; Footer
;;------------------------------------------------------------------------------
(def pp-license-url
  "https://github.com/comamitc/pretty-print.net/blob/master/LICENSE.md")
(def github-url "https://github.com/comamitc/pretty-print.net")
(def issues-url "https://github.com/comamitc/pretty-print.net/issues")
(def docs-url "http://pretty-print.net/#/about")

(rum/defc footer-docs-list < rum/static []
   [:div.col-ace4b
     [:ul
       ; [:li [:a.ftr-link-67c8e {:href docs-url} "Rationale"]]
       [:li [:a.ftr-link-67c8e {:href github-url} "GitHub"]]
       [:li [:a.ftr-link-67c8e {:href issues-url} "Issues"]]]])

(rum/defc footer-bottom < rum/static []
   [:div.bottom-31b43
     [:div.left-1764b
       [:p.small-14fbc
         "pretty-print.net is released under the "
         [:a {:href pp-license-url} "MIT License"] "."]]
     [:div.right-e461e
       [:a.ftr-home-link-2c3b4 {:href "/"} "pretty-print"
         [:span.ftr-info-a5716 ".net"]]]
     [:div.clr-43e49]])

(rum/defc footer < rum/static []
   [:div.ftr-outer-6bcd3
     [:div.container
       (footer-docs-list)
       [:div.clr-43e49]
       (footer-bottom)]])

;;------------------------------------------------------------------------------
;; NavBar
;;------------------------------------------------------------------------------
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

;;------------------------------------------------------------------------------
;; Main Page Contents
;;------------------------------------------------------------------------------
(rum/defc page-contents < rum/reactive []
  (let [*style (rum/cursor state :style)
        *error? (rum/cursor state :error?)]
    [:div#pageWrapper
      [:div.body-outer-7cb5e
        (nav-bar)
        [:div.container
          [:div
            [:button.u-pull-right.btn-5a8ac.button-primary
              {:on-click #(handle-on-click %)}
              "Format"]
            [:div.instructions-b15d3
              (str "Paste " ((rum/react *style) style-map)) ":"]]
          [:div.workspace-ca07e
            (code-editor (rum/react *style))
            (when (rum/react *error?)
              [:div.error-disp-7c4aa (str (rum/react *error?))])]]]
      (footer)]))

;;------------------------------------------------------------------------------
;; DOM mount
;;------------------------------------------------------------------------------
(defn main-page []
  (rum/mount (page-contents) (.getElementById js/document "bodyWrapper")))
