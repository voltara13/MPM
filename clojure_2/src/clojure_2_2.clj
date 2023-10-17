(ns clojure_2_2)

(def step 0.00001)

(defn calc-integral
  [f]
  (fn [x] (* (/ step 2)
             (+
              (f 0)
              (f x)
              (* 2 (apply + (map f 
                                 (take (int (/ x step)) 
                                       (iterate #(+ % step) step)))))))))