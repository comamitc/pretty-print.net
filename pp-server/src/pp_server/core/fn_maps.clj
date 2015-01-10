(ns pp-server.core.fn-maps
  (:require [pp-server.format.clojure-pp :refer [format-clj!]]
            [pp-server.format.web-pp :refer [format-json!]]
            [pp-server.format.data-pp :refer [format-xml!]]
            [pp-server.format.parse-error-handler :refer [parse-exception]]))

(def typefn
  {"clojure"  format-clj!
   "edn"      format-clj!
   "json"     format-json!
   "xml"      format-xml!})

(defn- to-lower
  [word]
  (clojure.string/lower-case word))

;; TODO: try catch here in case the parser throws an error
;; capture things like basic error and line number if possible
(defn mapfn
  "Retrieves a function from the typefn map to execute formatting. Function 
  signature must be `[input format-type]`."
  [input tipe]
  (let [type-norm (to-lower tipe)]
    (try 
      ((get typefn type-norm) input type-norm)
      ;; TODO: just captures the stacktrace so far
      (catch Exception e (parse-exception e))))) 