(ns pp-client.core
  (:require
    cljsjs.react
    [pp-client.html :as html]
    [pp-client.util :refer [js-log log]]))

(enable-console-print!)

;;------------------------------------------------------------------------------
;; Global Init
;;------------------------------------------------------------------------------
(html/init!)