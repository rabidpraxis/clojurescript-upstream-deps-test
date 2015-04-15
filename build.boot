(set-env!
  :source-paths  #{"src" "test"}
  :dependencies '[[org.clojure/clojurescript "0.0-3196"]
                  [adzerk/boot-test          "1.0.4"]
                  [cljsjs/react              "0.13.1-0"]])

(require '[adzerk.boot-test :refer :all])
