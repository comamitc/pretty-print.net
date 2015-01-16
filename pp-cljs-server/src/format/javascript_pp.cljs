(ns format.javascript-pp
  (:require [cljs.nodejs :as node]))

(def beautify (.-js_beautify (node/require "js-beautify")))

(defn format-js [input]
  ;; TODO: move settings to UI side
  (beautify input {:indent_size           2
                   :indent_char           " "
                   :max_preserve_newlines 2}))