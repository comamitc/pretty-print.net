(ns pp-client.data
  (:require
    [ajax.core :refer [GET POST]]
    [pp-client.util :refer [js-log log]]))

(defn format-input [state-atom handler error-handler]
  (POST (:uri @state-atom) {:params {:input (:value @state-atom)
                            :settings {}} ;;(:settings @state-atom)}
                            :format :json
                            :keywords? true
                            :response-format :json
                            :handler handler
                            :error-handler error-handler}))
