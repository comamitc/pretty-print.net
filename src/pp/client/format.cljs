(ns pp.client.format
  (:require [cljfmt.core :refer [reformat-string]]
            [pp.client.util :refer [js-log log]]
            [cljs.pprint :as p]
            [cljs.reader :as r]
            cljsjs.js-beautify
            cljsjs.parinfer))

;; try/catch everything
(defn- normalize-result [f & args]
  (try
    (assoc {} :result (apply f args))
    (catch js/Object e
      (assoc {} :error e))))

;; compose a function to transfor erroneous output
(def  format-clj (comp reformat-string
                       #(.-text %)
                       js/parinfer.indentMode))

(defn- pp-str [s]
  (-> s
      r/read-string
      (p/write :right-margin        80
               :stream              nil
               :miser-width         120
               :dispatch            p/simple-dispatch)))


(def format-edn (comp reformat-string
                      pp-str
                      #(.-text %)
                      js/parinfer.indentMode))

(defn format-input! [style input]
  (case style
    :clj  (normalize-result format-clj input)
    :edn  (normalize-result format-edn input)
    :json (normalize-result js/js_beautify input {:indent_size           2
                                                  :indent_char           " "
                                                  :max_preserve_newlines 2})))
