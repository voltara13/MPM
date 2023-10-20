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
(defn polinom [x] (- (* x x) (* 5 x) 3))

(deftest test-calc-integral
  (is (close-to 50 ((src/calc-integral linear) 10) 1e-3))
  (is (close-to 333.333 ((src/calc-integral quad) 10) 1e-3))
  (is (close-to 2500 ((src/calc-integral power) 10) 1e-3))
  (is (close-to 53.333 ((src/calc-integral polinom) 10) 1e-3)))

(defn -main []
  (run-tests 'clojure_2_0_test)
  (time ((src/calc-integral polinom) 1000))
  (time ((src/calc-integral polinom) 1000))
  (time ((src/calc-integral polinom) 900)))

(-main)