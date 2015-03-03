(ns pp-jvm.format.parse-error-handler
	(:require [cheshire.core :refer [generate-string]]))

(defn- extract-column
	[msg]
	(let [pat (re-pattern "column: (\\d+)|columnNumber: (\\d+)")
		  matcher (re-matcher pat msg)
		  found (re-find matcher)]
    (if (some? found)
      (or (found 1) (found 2))
      nil)))

(defn- extract-line
	[msg]
	(let [pat (re-pattern "line: (\\d+)|lineNumber: (\\d+)")
		  matcher (re-matcher pat msg)
		  found (re-find matcher)]
	  (if (some? found)
      (or (found 1) (found 2))
      nil)))

(defn parse-exception! 
  [msg]
  {:line (extract-line msg) :column (extract-column msg) :msg msg})