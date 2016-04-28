(ns pp-client.html.common
  (:require
    [quiescent :include-macros true]
    [sablono.core :as sablono :include-macros true]))

;;------------------------------------------------------------------------------
;; Helpers
;;------------------------------------------------------------------------------

(defn- url [path] path)
  ;;(if-let [base-href (:base-href config)]
    ;;(str (replace base-href #"/$" "") path)
    ;;path))

;;------------------------------------------------------------------------------
;; Footer
;;------------------------------------------------------------------------------

(def github-url "https://github.com/comamitc/pretty-print.net")
(def issues-url "https://github.com/comamitc/pretty-print.net/issues")
(def docs-url "http://pretty-print.net/#/about")

(defn footer-docs-list []
  (sablono/html
   [:div.col-ace4b
     [:ul
       [:li [:a.ftr-link-67c8e {:href docs-url} "Rationale"]]
       [:li [:a.ftr-link-67c8e {:href github-url} "GitHub"]]
       [:li [:a.ftr-link-67c8e {:href issues-url} "Issues"]]]]))

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
      ;;[:li [:a.ftr-link-67c8e {:href mailing-list-url}
      ;;  "Mailing List" [:i.fa.fa-external-link]]]
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
             "Issues" [:i.fa.fa-external-link]]]]]))

(def pp-license-url
  "https://github.com/comamitc/pretty-print.net/blob/master/LICENSE.md")

(defn footer-bottom []
  (sablono/html
   [:div.bottom-31b43
     [:div.left-1764b
       [:p.small-14fbc
         "pretty-print.net is released under the "
         [:a {:href pp-license-url} "MIT License"] "."]]
     [:div.right-e461e
       [:a.ftr-home-link-2c3b4 {:href (url "/")} "pretty-print"
         [:span.ftr-info-a5716 ".net"]]]
     [:div.clr-43e49]]))

(defn footer []
  (sablono/html
   [:div.ftr-outer-6bcd3
     [:div.ftr-inner-557c9
       (footer-docs-list)
      ;;(footer-learn-list)
      ;;(footer-community-list)
      ;;(footer-contribute-list)
       [:div.clr-43e49]
       (footer-bottom)]]))
