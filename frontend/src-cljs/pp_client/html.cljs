(ns pp-client.html
  (:require-macros [hiccups.core :as hiccups])
  (:require
    hiccups.runtime
    [pp-client.util :refer [js-log log]]))

;;------------------------------------------------------------------------------
;; HTML is super cool
;;------------------------------------------------------------------------------

(hiccups/defhtml header []
  [:header.outer-c5e7d
    [:div.inner-0f889
      [:div.left-9467a
        [:a.home-link-e4c1e {:href ""}
          "pretty-print"
          [:span.net-84e9a ".net"]]]
      [:div.right-1dd94 "right"]
      [:div.clr-217e3]]])

(hiccups/defhtml page []
  (header)
  [:div "I can quit anytime."]
  )
