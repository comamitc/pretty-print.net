(ns pp-server.format.utils
  (:require [clojure.tools.reader :as r]))

(defn str-to-literal!
  "Takes valid clojure as input and transforms it to a clojure literal for
  formating."
  [string]
  (r/read-string string))
