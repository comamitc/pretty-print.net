(ns pp-client.core
  (:use [jayq.core :only [$ add-class on parents prepend append]])
  (:require
    [pp-client.html :as html]
    [pp-client.util :refer [js-log log get-hash set-hash]]))

(enable-console-print!)

;;------------------------------------------------------------------------------
;; Global Init
;;------------------------------------------------------------------------------
(defn- validate-hash
  []
  (let [hash (get-hash)]
    (if (some? hash)
      hash
      "edn")))

(defn- init!
  "Global page init."
  []
  (append ($ "body") (html/page (validate-hash))))

($ init!)