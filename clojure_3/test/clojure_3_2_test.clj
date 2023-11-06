(ns clojure_3_2_test
  (:require [clojure.test :refer [deftest is run-tests]]
            [clojure_3_2 :as src]))

(deftest test-my-filter
  (is (= [2 4] (src/parallel-filter even? (apply list (range 1 6)) 7)))
  (is (= [1 3 5] (src/parallel-filter odd? (range 1 6) 2)))
  (is (= [1 3 5 7 9 11 13 15 17 19] (take 10 (src/parallel-filter odd? (range) 2))))
  (is (= [""] (src/parallel-filter empty? (apply list ["a" "" "c"]) 1)))
  (is (= ["a" "c"] (src/parallel-filter not-empty ["a" "" "c"] 1))))

(defn -main []
  (run-tests 'clojure_3_1_test))

(-main)
