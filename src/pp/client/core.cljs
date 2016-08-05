(ns pp.client.core
  (:require
    cljsjs.react
    [pp.client.routes :as routes]))

(enable-console-print!)

;;------------------------------------------------------------------------------
;; Global Init
;;------------------------------------------------------------------------------
(routes/init)
