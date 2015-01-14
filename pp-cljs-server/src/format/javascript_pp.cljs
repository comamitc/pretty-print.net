(ns format.javascript-pp
  (:require [cljs.nodejs :as node]))

(def beautify (.-js_beautify (node/require "js-beautify")))
(def default-settings {:indent_size           2
                       :indent_char           " "
                       :max_preserve_newlines 2   })

(defn format-js [input settings]
  ;; TODO: move settings to UI side
  (beautify input (merge default-settings settings)))