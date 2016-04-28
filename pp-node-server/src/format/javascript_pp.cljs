(ns format.javascript-pp
  (:require [cljs.nodejs :as node]))

(def beautify (node/require "js-beautify"))
(def beautify-js (.-js_beautify beautify))
(def beautify-css (.-css beautify))
(def beautify-html (.-html beautify))

(def default-settings {:indent_size           2
                       :indent_char           " "
                       :max_preserve_newlines 2})

;; TODO: refactor this
(defn format-js [input settings]
   (beautify-js input (merge default-settings settings)))

(defn format-css [input settings]
  (beautify-css input (merge default-settings settings)))

(defn format-html [input settings]
  (beautify-html input (merge default-settings settings)))
