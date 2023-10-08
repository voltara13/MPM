(ns clojure_2_0_test
  (:require [clojure.test :refer [deftest is run-tests]]
            [clojure_2_0 :as src]))

(defn abs
  [x]
  (max x (- x)))

(defn close-to
  [x y eps]
  (<= (abs (- x y)) eps))

(defn linear [x] x)
(defn quad [x] (* x x))
(defn power [x] (* x x x))
(defn indict [x] (reduce * (repeat x 5)))

(deftest test-calc-integral
  (is (close-to 50 ((src/calc-integral linear) 10) 1e-4))
  (is (close-to 333.35 ((src/calc-integral quad) 10) 1e-4))
  (is (close-to 2500.25 ((src/calc-integral power) 10) 1e-4))
  (is (close-to 11719058.8 ((src/calc-integral indict) 10) 1e-4)))

(defn -main []
  (run-tests 'clojure_2_0_test))

(-main)