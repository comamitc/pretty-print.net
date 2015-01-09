(ns pp-server.format.js-pp
  (:require [clojure.data.json :as json]
            [pp-server.format.utils :refer [str-to-literal!]]))

(defn format-js!
  [input tipe]
  (json/pprint (json/read-str input)))