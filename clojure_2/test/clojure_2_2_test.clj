(ns clojure_2_2_test
  (:require [clojure.test :refer [deftest is run-tests]]
            [clojure_2_2 :as src]
            [clojure_2_0_test :refer [close-to polinom indict linear power quad]]))

(deftest test-calc-integral
  (is (close-to 50 ((src/calc-integral linear) 10) 1e-3))
  (is (close-to 333.333 ((src/calc-integral quad) 10) 1e-3))
  (is (close-to 2500 ((src/calc-integral power) 10) 1e-3))
  (is (close-to 12206981.203 ((src/calc-integral indict) 10) 1e-3))
  (is (close-to 308033.428 ((src/calc-integral polinom) 100) 1e-3)))

(defn -main []
  (run-tests 'clojure_2_2_test)
  (println "OLD:" (time ((src/calc-integral polinom) 100)))
  (println "MEM FIRST:" (time ((src/calc-integral polinom) 100)))
  (println "MEM SECOND:" (time ((src/calc-integral polinom) 100))))

(-main)