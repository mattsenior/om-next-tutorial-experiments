(ns swapi.reconciler
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [om.next :as om]
            [swapi.state :refer [init]]
            [datascript.core :as d]
            [cljs.core.async :as async :refer [put! <! chan pipe]]
            [cognitect.transit :as t])
  (:import [goog Uri]
           [goog.net XhrIo]))

(defmulti read om/dispatch)

(defmulti mutate om/dispatch)

(def parser (om/parser {:read read :mutate mutate}))

(defn- url
  ([] (url nil))
  ([extra] (str "http://swapi.co/api" extra)))

(defn- xhr
  ([uri] (xhr (chan) uri))
  ([c uri]
   (.send XhrIo (Uri. uri) #(put! c (.-target %)))
   c))

(defn- json
  ([uri] (json (chan) uri))
  ([co uri]
   (let [tr (t/reader :json)
         xf (map #(->> % .getResponseText (t/read tr)))
         ci (chan 1 xf)]
     (xhr ci uri)
     (pipe ci co)
     co)))

(defn- extract-film [r]
  (-> r
      (select-keys ["title" "release_date"])
      (clojure.walk/keywordize-keys)
      (clojure.set/rename-keys
       {:title :film/name
        :release_date :film/year})
      (update-in [:film/year] #(subs % 0 4))))

(defn- extract-films [r]
  (->> (get r "results")
       (map extract-film)))

(def send-chan (chan))

(defn send-to-chan [c]
  (fn [{:keys [remote]} cb]
    (pr "remote" remote)
    (put! c {:cb cb})))
    ; (js/setTimeout
    ;   #(cb {:keys [:films/list]
    ;         :for-db [{:film/name "D"}
    ;                  {:film/name "E" :film/year 2009}
    ;                  {:film/name "F" :film/year 2010}]})
    ;   1000)))


(go
  (loop [{:keys [cb]} (<! send-chan)]
    (let [xf (map extract-films)
          ci (chan 1 xf)
          co (json ci (url "/films"))
          r (<! co)]
      (pr r)
      (cb {:keys [:films/list]
           :for-db r}))))

(def reconciler
  (om/reconciler
    {:state (init)
     :parser parser
     :send (send-to-chan send-chan)
     :merge
     (fn [reconciler state {:keys [keys for-db]}]
       (let [conn (-> reconciler :config :state)]
         (d/transact! conn for-db)
         {:keys keys :next @conn :tempids []}))}))
