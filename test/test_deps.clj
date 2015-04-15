(ns test-deps
  (:require [cljs.js-deps :as deps]
            [clojure.test :refer :all]))

;; Ripped out and rewrote for clarity a couple actions from closure.clj. The
;; most important bit is from:
;;
;; https://github.com/clojure/clojurescript/blob/master/src/clj/cljs/closure.clj#L1248
(defn load-lib [inject-fn]
  (-> (Thread/currentThread)
      .getContextClassLoader
      (.getResources "deps.cljs")
      enumeration-seq
      inject-fn
      (->> (map (comp read-string slurp))
           (apply merge-with concat))
      deps/library-dependencies
      deps/build-index))

(deftest deps-loading
  (testing "loading with parent deps.cljs override"
    (is (not= (get-in (load-lib identity) ["cljsjs.react" :file])
              "cljsjs/production/react-with-addons.min.inc.js")))
  (testing "loading with local deps.cljs override"
    (is (= (get-in (load-lib reverse) ["cljsjs.react" :file])
           "cljsjs/production/react-with-addons.min.inc.js"))))
