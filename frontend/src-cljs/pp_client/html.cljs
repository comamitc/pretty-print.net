(ns pp-client.html
  (:require-macros [hiccups.core :as hiccups])
  (:require
    hiccups.runtime
    [pp-client.util :refer [js-log log]]))

(hiccups/defhtml page []
  [:div "I can quit anytime."]
  )
