(ns pp-jvm.format.clojure-pp
  (:require [clj-beautify.core :refer [format-string]]))

(defn format-clj
  "Clojure formatting function that takes a unformatted-input and format type
  and transforms it into a formatted output using clojure.pprint/write."
  [input format-type]
  (format-string input format-type))