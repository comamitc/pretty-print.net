(ns pp-server.format.web-pp
  (:require [cheshire.core :refer [parse-string generate-string]]))

(defn format-json!
  "Takes valid JSON data and formats it using cheshire.core/generate-string"
  [input tipe]
  ;; TODO: doesn't seem very efficient since the JSON is already in str form
  (generate-string (parse-string input true)
                   {:pretty true}))