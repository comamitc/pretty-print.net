(ns pp-jvm.core.fn-maps
  (:require [pp-jvm.format.clojure-pp :refer [format-clj]]
            [pp-jvm.format.web-pp :refer [format-json]]
            [pp-jvm.format.data-pp :refer [format-xml]]
            [pp-jvm.format.parse-error-handler :refer [parse-exception!]]
            [clojure.tools.logging :as log]
            [cheshire.core :refer [generate-string]]))

;; TODO we don't use settings
;; TODO we don't use tipe
(defn- format-scala
  [input tipe settings] 
  (ppjvm.format.ScalaFormat/formatScala input))

(def typefn
  {"clj"      format-clj
   "edn"      format-clj
   "json"     format-json
   "xml"      format-xml
   "scala"    format-scala})

(defn- to-lower
  [word]
  (clojure.string/lower-case word))

(defn mapfn
  "Retrieves a function from the typefn map to execute formatting. Function
  signature must be `[input format-type]`."
  [input tipe settings]
  (let [type-norm (to-lower tipe)]
    (try
      {:status 200
       :headers {"Content-Type" "application/json"}
       :body (generate-string ((get typefn type-norm) input 
                                                      type-norm 
                                                      settings))}
      (catch Exception e
        (let [err-msg (.getMessage e)]
          (log/error e)
           {:status 400
            :headers {"Content-Type" "application/json"}
            :body (generate-string (parse-exception! err-msg))})))))