(ns pp-server.core.fn-maps
  (:require [pp-server.format.clojure-pp :refer [format-clj!]]
            [pp-server.format.web-pp :refer [format-json!]]))

(def typefn
  {"clojure"  format-clj!
   "edn"      format-clj!
   "json"     format-json!})

(defn mapfn
  [input tipe]
  ((get typefn tipe) input tipe))