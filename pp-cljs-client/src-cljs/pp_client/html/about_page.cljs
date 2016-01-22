(ns pp-client.html.about-page
  (:require 
    [pp-client.html.common :refer [footer]]
    [pp-client.dom :refer [by-id]]
    [quiescent :include-macros true]
    [sablono.core :as sablono :include-macros true]))

(defn HeaderNoState []
  (sablono/html
    [:header.header-outer-c5e7d
      [:div.header-inner-0f889
        [:div.left-9467a
          [:a.home-link-e4c1e {:href ""}
            "pretty-print"
            [:span.net-84e9a ".net"]]]
        [:div.clr-217e3]]]))

(defn AboutPage []
  (sablono/html
    [:div#pageWrapper
      (HeaderNoState)
      [:div.body-outer-7cb5e
        [:div.body-inner-5a8ac
          [:h2.instructions-b15d3 "Rationale"]
          [:p.text-block-d714f
          (str "EDN is a powerful data interchange format commonly used with "
            "Clojure  and ClojureScript programs. It also doubles as a literal "
            "representation of most Clojure data structures, which are often "
            "printed to a REPL or console environment for debugging purposes. ")]
          [:p.text-block-d714f
            (str "Clojure core comes with clojure.pprint - which is a library to "
            "format Clojure code (and thus EDN) in a human-readable fashion. ")]
          [:p.text-block-d714f
            (str "As of January 2015, there are numerous online services to pretty "
            "print various data interchange formats (JSON, YAML, XML, etc), "
            "but there is no online service for printing an EDN string.")]
          [:p.text-block-d714f
            (str "This project aims to build such a service and improve the "
            "usability of working with EDN data.")]
        [:div.clr-217e3]]]
      (footer)]))

(defn about-init! [] (quiescent/render (AboutPage) (by-id "bodyWrapper")))
