(ns swapi.devcards.core
  (:require-macros [devcards.core :refer [defcard]])
  (:require [om.next :as om]
            [om.dom :as dom]
            [sablono.core :as html :refer-macros [html]]
            [swapi.core :as ll]))

(enable-console-print!)

(defcard
  "# Learning Ladders
   More written here")


(defcard test-card
  "### A rung"
  (html
    [:div
     [:p "Hello"]]))

(defcard test-card
  "### A rung"
  [:div
    [:p "Hello"]])

(defcard test-card
  "### A rung"
  '(1 2 3))

(defcard counter-atom
  (fn [data-atom owner]
    (html
      [:div
       [:h3 "Example Counter w/Initial Data: " (:count @data-atom)]
       [:button
        {:onClick (fn [] (swap! data-atom update-in [:count] inc))}
        "inc"]]))
  {:count 45}
  {:inspect-data true})

(defcard ll
  (fn [props _] (ll/animal-list-item @props))
  {:name "Lion"})
