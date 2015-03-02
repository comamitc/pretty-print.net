(ns pp-client.data
  (:require
    [ajax.core :refer [GET POST]]
    [pp-client.util :refer [js-log log]]))

(defn- error-handler [response]
  (if (= (:status response) 400)

    (let [resp (:response response)]
      (js-log (str "an parse error occured " resp)))

    (js-log (str "some other error occured " (:status-text response)))))

(def default-opts {:keywords? false
                   :error-handler error-handler})

(defn format-input [url payload handler]
  (POST url (assoc default-opts
                   :params {:input payload}
                   :format :json
                   :handler handler)))
