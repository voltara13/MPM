(ns clojure_1_3_test
  (:require [clojure.test :refer [deftest is run-tests]]
            [clojure_1_3 :as src]))

(deftest test-my-map
  (is (= [1 4 9 16 25]    (src/my-map (fn [x] (* x x)) (range 1 6))))
  (is (= [1 1 1 1 1]      (src/my-map (fn [x] (/ x x)) (range 1 6))))
  (is (= [2 4 6 8 10]     (src/my-map (fn [x] (+ x x)) (range 1 6))))
  (is (= [0 0 0 0 0]      (src/my-map (fn [x] (- x x)) (range 1 6)))) 
  (is (= ["aa" "bb" "cc"] (src/my-map (fn [x] (+ x x)) ["a" "b" "c"])))
  (is (= ["a0" "b0" "c0"] (src/my-map (fn [x] (+ x 0)) ["a" "b" "c"]))))

(deftest test-my-filter
  (is (= [2 4] (src/my-filter even? (range 1 6))))
  (is (= [1 3 5] (src/my-filter odd? (range 1 6))))
  (is (= [""] (src/my-filter empty? ["a" "" "c"])))
  (is (= ["a" "c"] (src/my-filter not-empty ["a" "" "c"]))))

(defn -main []
  (run-tests 'clojure_1_3_test))

(-main)
