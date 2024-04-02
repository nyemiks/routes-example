(ns routes-example.routes
  (:require
   [bidi.bidi :as bidi]
   [pushy.core :as pushy]
   [re-frame.core :as re-frame]
   [routes-example.events :as events]))

(defmulti panels identity)
(defmethod panels :default [] [:div "No panel found for this route."])

(def routes
  (atom
    ["/" {""      :home
          "about" :about
          "users" {
                   "" :users-index
                   ["/" :id] :user-view 
                   }

          }
     ]
   )
  )

(defn parse
  [url]
  (bidi/match-route @routes url))

;(parse "/users/2")

(defn url-for
  [& args]
  (apply bidi/path-for (into [@routes] args)))

;(url-for :about)

(defn dispatch
  [route]
  (let [panel (keyword (str (name (:handler route)) "-panel"))]
  ;  (re-frame/dispatch [::events/set-active-panel panel])
     (re-frame/dispatch [::events/set-route {:route route :panel panel}])
    )
  )

; returns panel name (as a keyword) based on supplied url
;(keyword (str (name (:handler (parse "/users/1"))) "-panel"))

(defonce history
  (pushy/pushy dispatch parse))

(defn navigate!
  [handler]
  (pushy/set-token! history (apply url-for handler)))

;(apply url-for [:user-view :id 10])

(defn start!
  []
  (pushy/start! history))

(re-frame/reg-fx
  :navigate
  (fn [handler]
    (navigate! handler)))
