(ns swapi.parsing.films
  (:require [swapi.reconciler :refer [read]]
            [datascript.core :as d]))

(defmethod read :films/list
  [{:keys [state query ast]} key params]
  (pr "query" query)
  (pr "ast" ast)
  (let [films (d/q '[:find [(pull ?e ?selector) ...]
                     :in $ ?selector
                     :where [?e :film/name]]
                (d/db state) query)
        films (sort-by :film/year films)]
    (pr "films" films)
    {:remote ast
     :value films}))
