(ns swapi.state
  (:require [datascript.core :as d]))

(defn init []
  (let [schema {:film/name {:db/unique :db.unique/identity}}
        conn   (d/create-conn schema)]
    ; (d/transact! conn)
    ;   [{:film/name "A" :film/year 1960}
    ;    {:film/name "B" :film/year 1970}
    ;    {:film/name "C" :film/year 1980}])
    conn))
