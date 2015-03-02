(ns pp-client.data
  (:require
    [ajax.core :refer [GET POST]]
    [pp-client.util :refer [js-log log]]))

(defn- error-handler [{:keys [status status-text]}]
  (if (= status 400)
    (js-log (str "an parse error occured " status-text))
    (js-log (str "some other error occured" status-text))))

(def default-opts {;;:response-format :json
                   :keywords? false
                   :error-handler error-handler})

(defn format-input [url payload handler]
  (POST url (assoc default-opts
                   :params {:input payload}
                   :format :json
                   :handler handler)))
