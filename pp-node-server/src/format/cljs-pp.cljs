(ns format.clj-pp
  (:require [cljs.pprint :refer [write
                                 *print-pretty*
                                 *print-pprint-dispatch*
                                 *print-right-margin*
                                 *print-miser-width*
                                 *print-base*
                                 *print-radix*
                                 *print-suppress-namespaces*
                                 code-dispatch
                                 simple-dispatch]]
            [cljs.reader :as reader]))

(def dispatch-mode {"clj" code-dispatch
                    "edn" simple-dispatch})

(def default-settings {:right-margin        *print-right-margin*
                       :miser-width         *print-miser-width*
                       :base                *print-base*
                       :level               *print-level*
                       :radix               *print-radix*
                       :suppress-namespaces *print-suppress-namespaces*
                       :pretty              *print-pretty*
                       :dispatch            *print-pprint-dispatch*})

(defn format-string
  [input mode settings]
  (let [presets (merge default-settings settings)
        result  (write (reader/read-string input)
                       :pretty              (:pretty presets)
                       :stream              nil
                       :right-margin        (:right-margin presets)
                       :miser-width         (:miser-width presets)
                       :base                (:base presets)
                       :length              (:length presets)
                       :radix               (:radix presets)
                       :suppress-namespace  (:suppress-namespace presets)
                       :dispatch            (get dispatch-mode format-type))]
    (print result)
    result))

(defn format-clj [tipe input settings]
  ([input mode] (format-clj input mode nil))
  ([input mode settings] (format-string input mode settings)))
