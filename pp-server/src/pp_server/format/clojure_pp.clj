(ns pp-server.format.clojure-pp
  (:require [clojure.tools.reader :as r]
            [clojure.pprint :refer [write]]))

(defn- str-to-literal!
  "Takes valid clojure as input and transforms it to a clojure literal for
  formating."
  [string]
  (r/read-string string))

(defn- format-literal!
  "Takes a literal and formats it using the built in clojure.pprint/write
  function. Returns a formatted literal."
  [literal]
  (write literal :pretty true :stream nil :readably* true))

(defn- literal-to-str!
  "Takes valid formatter clojure literal as input and transforms it to a string
  for return to webapp UI"
  [literal]
  (str literal))

(defn format-clj!
  [input]
  (let [in-lit    (str-to-literal! input)
        formatted (format-literal! in-lit)
        out-str   (literal-to-str! formatted)]
        (println out-str)
        out-str))