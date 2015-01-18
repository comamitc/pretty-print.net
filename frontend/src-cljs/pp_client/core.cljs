(ns pp-client.core
  (:require
    [pp-client.html :as html]
    [pp-client.util :refer [js-log log]]))

(enable-console-print!)

(def $ js/jQuery)

;;------------------------------------------------------------------------------
;; Global Init
;;------------------------------------------------------------------------------

(defn- init!
  "Global page init."
  []
  (.append ($ "body") (html/page)))

($ init!)
