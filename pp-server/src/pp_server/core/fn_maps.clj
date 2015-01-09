(ns pp-server.core.fn-maps
  (:require [pp-server.format.clojure-pp :refer [format-clj!]]
            [pp-server.format.web-pp :refer [format-json!]]
            [pp-server.format.data-pp :refer [format-xml!]]))

;; TODO: normalization of case for type
(def typefn
  {"clojure"  format-clj!
   "edn"      format-clj!
   "json"     format-json!
   "xml"      format-xml!})

;; TODO: try catch here in case the parser throws an error
;; capture things like basic error and line number if possible
(defn mapfn
  [input tipe]
  ((get typefn tipe) input tipe))