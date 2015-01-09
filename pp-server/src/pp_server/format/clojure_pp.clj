(ns pp-server.format.clojure-pp
  (:require [clojure.tools.reader :as r]
            [clojure.pprint :refer [write code-dispatch simple-dispatch]]
            [pp-server.format.utils :refer [str-to-literal!]]))

(def dispatch-mode {:clojure code-dispatch :edn simple-dispatch})

(defn- format-literal!
  "Takes a literal and formats it using the built in clojure.pprint/write
  function. Returns a formatted literal."
  [literal tipe]
  (write literal
         :pretty true
         :stream nil
         :dispatch ((keyword tipe) dispatch-mode)))

(defn- literal-to-str!
  "Takes valid formatter clojure literal as input and transforms it to a string
  for return to webapp UI"
  [literal]
  (str literal))

(defn format-clj!
  "Clojure formatting function that takes a unformatted-input and format type
  and transforms it into a formatted output using clojure.pprint/write."
  [input tipe]
  (let [in-lit    (str-to-literal! input)
        formatted (format-literal! in-lit tipe)]
        (literal-to-str! formatted)))