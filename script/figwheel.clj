(require '[figwheel-sidecar.repl :as r]
  '[figwheel-sidecar.repl-api :as ra])

(ra/start-figwheel!
  {:figwheel-options {}
   :build-ids ["dev" "devcards"]
   :all-builds
   [{:id "dev"
     :figwheel true
     :source-paths ["src"]
     :compiler {:main 'swapi.core
                :asset-path "js"
                :output-to "resources/public/js/main.js"
                :output-dir "resources/public/js"
                :verbose true}}
    {:id "devcards"
     :figwheel {:devcards true}
     :source-paths ["src"]
     :compiler {:main 'swapi.devcards.core
                :asset-path "/devcards/out"
                :output-to "resources/public/devcards/main.js"
                :output-dir "resources/public/devcards/out"
                :source-map-timestamp true}}]})

(ra/cljs-repl)
