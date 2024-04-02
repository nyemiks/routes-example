(ns routes-example.users-views.views
  (:require
   [re-frame.core :as re-frame]
   [routes-example.users-views.subs :as subs]
   [routes-example.routes :as routes]
   [routes-example.subs :as route-subs]
   )
  )


(defn user-view []
  (let 
       [
          route-params @(re-frame/subscribe [::route-subs/route-params])
          user @(re-frame/subscribe [::subs/user (:id route-params)])
        ]
          (.info js/console "route-params: " route-params)
         (.info js/console "user: " user)
        [:div (str "The selected user is ") (:name user)]
    )
  )


(defmethod routes/panels :user-view-panel [] [user-view])