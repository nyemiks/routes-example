(ns routes-example.db)

(def default-db
  {:name "re-frame"
   :users [{:id 1 :name "on the code again"} {:id 2 :name "Gareth Cliff"}]
   
   }
  )


(defn get-user-id [ user-id]
   (first (filter (fn [u]
             (= (:id u) (int user-id))
             ) 
           (:users default-db)
           )
          )
   )

(get-user-id "2")