(ns pp-server.format.parse-error-handler
	(:require [cheshire.core :refer [generate-string]]))

(defn- extract-column
	[msg]
	(let [pat (re-pattern "column: (\\d+)|columnNumber: (\\d+)")
		  matcher (re-matcher pat msg)
		  found (re-find matcher)]
		  (or (found 1) (found 2))))

(defn- extract-line
	[msg]
	(let [pat (re-pattern "line: (\\d+)|lineNumber: (\\d+)")
		  matcher (re-matcher pat msg)
		  found (re-find matcher)]
		  (or (found 1) (found 2))))

(defn- error-obj
	[msg]
	{:line (extract-line msg) :column (extract-column msg) :msg msg})

(defn parse-exception!
  [err]
  (let [msg (.getMessage err)]
  	(generate-string {:line (extract-line msg) 
  		              :column (extract-column msg) 
  		              :msg msg})))