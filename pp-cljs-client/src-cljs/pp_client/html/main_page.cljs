(ns pp-client.html.main-page
  (:require 
    [pp-client.dom :refer [by-id]]
    [pp-client.util :refer [js-log log]]
    [pp-client.data :refer [format-input set-localstorage!]]
    [pp-client.html.common :refer [footer]]
    [pp-client.config :refer [state]]
    [quiescent :include-macros true]
    [sablono.core :as sablono :include-macros true]))

;;------------------------------------------------------------------------------
;; Helpers
;;------------------------------------------------------------------------------

(defn- normalize-settings [curr-state]
  (let [settings (:settings curr-state)
        y (into {} (for [[k v] settings] [k (or (:value v) (:checked v))]))
        new-sets (assoc-in curr-state [:settings] y)]
    new-sets))

;;------------------------------------------------------------------------------
;; Event triggers
;;------------------------------------------------------------------------------

;; TODO: need to format "settings" properly before sending to server
(defn- on-format [response]
  (swap! state assoc :value response :success? true :error? false))

(defn- on-error [response]
  (if (= (:status response) 400)
    (let [resp (:response response)]
      (swap! state conj resp {:success? false :error? true}))
    ;; unknown server error
    (throw (:status-text response))))

(defn- on-style-change [evt]
  (aset js/window "location" "hash" (str "/format/" (-> evt .-target .-value))))

(defn- on-btn-click [evt]
  (set-localstorage! (:id @state) (:settings @state))
  (format-input (normalize-settings @state) on-format on-error))

(defn- reset-state! [new-state]
  (reset! state (assoc new-state :success? false :error? false)))

(defn- on-range-change [k evt new-state]
  (reset-state!
    (assoc-in new-state [:settings k :value] (int (-> evt .-target .-value)))))

(defn- on-checkbox-change [k evt new-state]
  (reset-state!
    (assoc-in
      new-state
      [:settings k :checked]
      (false? (get-in new-state [:settings k :checked] false)))))

(def event-map {"range" on-range-change, "checkbox" on-checkbox-change})

(defn- on-change-evt [k]
  (fn [evt] ((get event-map (-> evt .-target .-type)) k evt @state)))

;;------------------------------------------------------------------------------
;; Header
;;------------------------------------------------------------------------------

(quiescent/defcomponent InputOptions [new-state]
  (sablono/html
    [:select#inputSelect.big-select-51b29
      {:on-change #(on-style-change %1) :value (:id new-state)}
      [:optgroup {:label "Clojure"}
        [:option {:value "clj"} "Clojure Code"]
        [:option {:value "edn"} "EDN"]
      ;;[:optgroup {:label "JavaScript"}
      ;;  [:option {:value "js"} "JavaScript Code"]
      ;;  [:option {:value "json"} "JSON"]]
        ]]))

(quiescent/defcomponent Header [state]
  (sablono/html
    [:header.header-outer-c5e7d
      [:div.header-inner-0f889
        [:div.left-9467a
          [:a.home-link-e4c1e {:href ""}
            "pretty-print"
            [:span.net-84e9a ".net"]]]
        [:div.right-1dd94
          (InputOptions state)]
        [:div.clr-217e3]]]))

;;------------------------------------------------------------------------------
;; Body
;;------------------------------------------------------------------------------

(quiescent/defcomponent SettingsAction [settings]
  (sablono/html
    [:div.settings-wrapper-751dc
      [:div.settings-hdr-fa6ca "Settings"]
      (map (fn [[k v]]
              [:div.setting-e0ddb
                [:div.setting-label-abd34 
                  (str (:name v) " ") [:span.set-value-06ba3 (:value v) ]]
                [:div
                  [:input
                    (assoc v :on-change (on-change-evt k))]
                  [:label.label-f831f {:for (:id v)}]]]) settings)]))

(quiescent/defcomponent FormatAction [new-state]
  (sablono/html
    [:div.format-action-7ac2a
      [:button#formatBtn.btn-2d976
          {:on-click #(on-btn-click %1)
           :disabled (if (empty? (:value new-state)) true false)}
            "Format"]
        (when (:error? new-state)
          [:div.error-disp-7c4aa
            [:i.fa.fa-times-circle.fa-2]
            [:span.err-ttl-0867b "Format Error"]
              [:div.msg-6f5ee (:msg new-state)]
              (when (some? (:line new-state))
                [:div.line-b55e8 (str "@line: " (:line new-state))
              (when (some? (:column new-state))
                (str "; column: " (:column new-state)))])])
        (when (:success? new-state)
          [:div.success-disp-3a51b
            [:i.fa.fa-check-circle.fa-2]
            [:span.success-ttl-390ee "Success"]])]))

(quiescent/defcomponent RightBody [new-state]
  (sablono/html
    (let [settings (:settings new-state)]
      [:div.right-body-caf9a
        (when (not (empty? settings)) (SettingsAction settings))
        (FormatAction new-state)])))

(quiescent/defcomponent LeftBody [new-state]
  (sablono/html
    [:div.left-body-ca07e
      [:textarea#mainTextarea.text-f3988
        {:value (:value new-state)
         :on-change #(swap! state assoc :value (-> % .-target .-value)
                                        :success? false
                                        :error? false)}]]))

(quiescent/defcomponent MainPage [state]
  (sablono/html
    [:div#pageWrapper
      (Header state)
      [:div.body-outer-7cb5e
            [:div.body-inner-40af1
              [:h2.instructions-b15d3 (str "Paste " (:desc state) ":")]
              (LeftBody state)
              (RightBody state)
              [:div.clr-217e3]]]
      (footer)]))

;;------------------------------------------------------------------------------
;; State Change and Rendering
;;------------------------------------------------------------------------------

(def request-anim-frame (aget js/window "requestAnimationFrame"))
(def cancel-anim-frame (aget js/window "cancelAnimationFrame"))
(def anim-frame-id nil)

(defn- on-change-state [_kwd _the-atom _old-state new-state]
  ;; cancel any previous render functions if they happen before the next
  ;; animation frame
  (when anim-frame-id
    (cancel-anim-frame anim-frame-id)
    (set! anim-frame-id nil))

  ;; put the render function on the next animation frame
  (let [render-fn #(quiescent/render (MainPage new-state) (by-id "bodyWrapper"))]
    (set! anim-frame-id (request-anim-frame render-fn))))

(add-watch state :main on-change-state)

;;------------------------------------------------------------------------------
;; init
;;------------------------------------------------------------------------------
(defn main-init! [style] (swap! state conj style))