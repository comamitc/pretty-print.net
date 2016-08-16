(ns pp.client.format
  (:require [cljfmt.core :refer [reformat-string]]
            [pp.client.util :refer [js-log log]]
            cljsjs.js-beautify
            cljsjs.parinfer))

;; try/catch everything
(defn- normalize-result [f & args]
  (try
    (assoc {} :result (apply f args))
    (catch js/Object e
      (assoc {} :error e))))

;; compose a function to transfor erroneous output
(def format-clj (comp reformat-string
                      #(.-text %)
                      js/parinfer.indentMode))

(defn format-input! [style input]
  (case style
    :clj  (normalize-result format-clj input)
    :json (normalize-result js/js_beautify input {:indent_size           2
                                                  :indent_char           " "
                                                  :max_preserve_newlines 2})))
