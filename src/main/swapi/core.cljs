(ns swapi.core
  (:require [goog.dom :as gdom]
            [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [sablono.core :as html :refer-macros [html]]
            [swapi.reconciler :refer [reconciler read]]
            [swapi.parsing.films]))

(enable-console-print!)

(defui FilmListItem
  static om/IQuery
  (query [this]
    [:film/name :film/year])
  Object
  (render [this]
    (let [{:keys [film/name film/year]} (om/props this)]
      (html
        [:li (str name (when year (str " (" year ")")))]))))

(def film-list-item (om/factory FilmListItem))

(defn film-list [films]
  (html
    [:div
      [:h2 "Films"]
      [:ol (map film-list-item films)]]))

(defui App
  static om/IQuery
  (query [this]
    (pr "IQuery")
    `[{:films/list ~(om/get-query FilmListItem)}])
  Object
  (render [this]
    (pr "props" (om/props this))
    ; (let [list (select-keys (om/props this) [:films/list])]
    (let [list (:films/list (om/props this))]
      (html
        [:div
          [:header
            [:h1 "SWAPI"]]
          [:main
            (film-list list)]]))))

(defn run []
  (om/add-root! reconciler App (gdom/getElement "app")))

(run)
