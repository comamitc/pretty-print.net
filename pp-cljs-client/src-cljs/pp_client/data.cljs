(ns pp-client.data
  (:require
    [ajax.core :refer [GET POST]]
    [pp-client.util :refer [js-log log]]))

(defn format-input [url payload handler error-handler]
  (POST url {:params {:input payload :settings {}}
             :format :json
             :keywords? true
             :response-format :json
             :handler handler
             :error-handler error-handler}))
