(ns pp-client.data
  (:require
    [ajax.core :refer [GET POST]]
    [pp-client.util :refer [js-log log]]))

(defn format-input [state handler error-handler]
  (POST (:uri state) {:params {:input (:value state)
                            :settings (:settings state)} ;;(:settings @state-atom)}
                            :format :json
                            :keywords? true
                            :response-format :json
                            :handler handler
                            :error-handler error-handler}))
