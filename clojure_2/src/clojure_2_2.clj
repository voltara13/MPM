(ns clojure_2_2)

(def step 0.001)

(defn calc-integral
  [f]
  (fn [x] (* (/ step 2)
             (+
              (f 0)
              (f x)
              (* 2 (nth (map first (iterate
                                    (fn [[prev-value x]]
                                      [(+ prev-value (f x)) (+ step x)])
                                    [(f step) (+ step step)])) 
                        (- (int (/ x step)) 2)))))))
