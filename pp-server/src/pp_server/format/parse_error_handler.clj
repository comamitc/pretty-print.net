(ns pp-server.format.parse-error-handler)

(defn parse-exception
  [err]
  (.getMessage err))