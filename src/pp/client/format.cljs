(ns pp.client.format
  (:require [cljfmt.core :refer [reformat-string]]
            [pp.client.util :refer [js-log log]]
            [clojure.walk :refer [keywordize-keys]]
            [cljs.pprint :as p]
            [cljs.reader :as r]
            [camel-snake-kebab.core :refer [->kebab-case-keyword]]
            [camel-snake-kebab.extras :refer [transform-keys]]
            cljsjs.js-beautify
            cljsjs.parinfer))

;; try/catch everything
(defn- normalize-result [f & args]
  (try
    (assoc {} :result (apply f args))
    (catch js/Object e
      (assoc {} :error e))))

(def js-settings {:indent_size           2
                  :indent_char           " "
                  :max_preserve_newlines 2})

;; compose a function to transfor erroneous output
(def format-clj (comp reformat-string
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

(def edn->json
  (let [beautify (fn [in] (js/js_beautify in js-settings))]
    (comp beautify
          #(.stringify js/JSON %)
          clj->js
          r/read-string)))

(def json->edn
  (comp
    format-edn
    str
    #(transform-keys ->kebab-case-keyword %)
    js->clj
    #(.parse js/JSON %)))

(defn format-input! [style input]
  (case style
    :clj  (normalize-result format-clj input)
    :edn  (normalize-result format-edn input)
    :json (normalize-result js/js_beautify input js-settings)
    :edn->json (normalize-result edn->json input)
    :json->edn (normalize-result json->edn input)))
