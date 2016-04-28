(ns format.clj-pp
  (:require [cljs.nodejs :as node]
            [utils :as util]))

(def parinfer (node/require "parinfer"))

(defn format-clj [input _]
  (let [result   (.parenMode parinfer input)
        success? (.-success result)]
    (if success?
      (.-text result)
      (throw (js/Error. (-> result
                            .-error
                            .-message))))))
