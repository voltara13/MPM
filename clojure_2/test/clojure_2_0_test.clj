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
(defn polinom [x] (- (* x x) (* 5 x) 3))

(deftest test-calc-integral
  (is (close-to 50 ((src/calc-integral linear) 10) 1e-3))
  (is (close-to 333.333 ((src/calc-integral quad) 10) 1e-3))
  (is (close-to 2500 ((src/calc-integral power) 10) 1e-3))
  (is (close-to 12206981.203 ((src/calc-integral indict) 10) 1e-3))
  (is (close-to 308033.238 ((src/calc-integral polinom) 100) 1e-3)))

(defn -main []
  (run-tests 'clojure_2_0_test))

(-main)