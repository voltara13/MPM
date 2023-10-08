(ns clojure_2_1_test
  (:require [clojure.test :refer [deftest is run-tests]]
            [clojure_2_0 :as old]
            [clojure_2_1 :as mem]
            [clojure_2_0_test :refer [close-to polinom indict linear power quad]]))

(deftest test-calc-integral
  (is (close-to 50 ((mem/calc-integral-mem linear) 10) 1e-3))
  (is (close-to 333.333 ((mem/calc-integral-mem quad) 10) 1e-3))
  (is (close-to 2500 ((mem/calc-integral-mem power) 10) 1e-3))
  (is (close-to 12206981.203 ((mem/calc-integral-mem indict) 10) 1e-3))
  (is (close-to 308033.238 ((mem/calc-integral-mem polinom) 100) 1e-3)))

(defn -main []
  (run-tests 'clojure_2_1_test)
  (println "OLD:" (time ((old/calc-integral polinom) 100)))
  (println "MEM FIRST:" (time ((mem/calc-integral-mem polinom) 100))))

(-main)