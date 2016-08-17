(ns pp.client.dom
  (:require
    goog.dom
    [pp.client.util :as util]))

;;------------------------------------------------------------------------------
;; Some Native DOM Helper Functions
;;------------------------------------------------------------------------------
(defn by-id [id]
  (.getElementById js/document id))
