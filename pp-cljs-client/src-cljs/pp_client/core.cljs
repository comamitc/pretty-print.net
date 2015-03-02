(ns pp-client.core
  (:require
    cljsjs.react    
    [pp-client.routes :as routes]
    [pp-client.util :refer [js-log log]]))

(enable-console-print!)

;;------------------------------------------------------------------------------
;; Global Init
;;------------------------------------------------------------------------------
(routes/init)