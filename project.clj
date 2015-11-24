(defproject swapi "0.1.0-SNAPSHOT"
  :description "SWAPI"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.170"]
                 [com.cognitect/transit-cljs "0.8.232"]
                 [org.omcljs/om "1.0.0-alpha23"]
                 [sablono "0.4.0"]
                 [datascript "0.13.3"]
                 [figwheel-sidecar "0.5.0-SNAPSHOT" :scope "test"]
                 [devcards "0.2.1" :scope "test"]]
  :source-paths  ["src/main" "src/devcards"])
