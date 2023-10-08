(ns clojure_2_0)

(def step 0.00001)

(defn calc-integral
  [f]
  (fn [x] (* (/ step 2)
             (+
              (f 0)
              (f x)
              (* 2 (reduce + (map f (range step (- x step) step))))))))