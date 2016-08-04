(ns format.clj-pp
  (:require [cljfmt.core :refer [reformat-string]]))

(defn format-clj
  [input]
  (reformat-string input))
