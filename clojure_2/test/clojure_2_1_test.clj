(ns clojure_2_1_test
  (:require [clojure.test :refer [deftest is run-tests]]
            [clojure_2_0 :as src]
            [clojure_2_0_test :refer [close-to polinom indict linear power quad]]))

(def linear-mem (memoize linear))
(def quad-mem (memoize quad))
(def power-mem (memoize power))
(def indict-mem (memoize indict))
(def polinom-mem (memoize polinom))

(deftest test-calc-integral
  (is (close-to 50 ((src/calc-integral linear-mem) 10) 1e-3))
  (is (close-to 333.333 ((src/calc-integral quad-mem) 10) 1e-3))
  (is (close-to 2500 ((src/calc-integral power-mem) 10) 1e-3))
  (is (close-to 12206981.203 ((src/calc-integral indict-mem) 10) 1e-3))
  (is (close-to 308033.238 ((src/calc-integral polinom-mem) 100) 1e-3)))

(defn -main []
  (run-tests 'clojure_2_1_test)
  (println "OLD:" (time ((src/calc-integral polinom-mem) 100)))
  (println "MEM FIRST:" (time ((src/calc-integral polinom-mem) 100)))
  (println "MEM SECOND:" (time ((src/calc-integral polinom-mem) 100))))

(-main)