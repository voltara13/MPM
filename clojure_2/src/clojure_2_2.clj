(ns clojure_2_2)

(def step 0.001)

(defn calc-integral
  [f]
  (let [results (map first (iterate
                            (fn [[prev-value x]]
                              [(+ prev-value (f x)) (+ step x)])
                            [(f step) (+ step step)]))]
    (fn [x] (* (/ step 2)
               (+
                (f 0)
                (f x)
                (* 2 (nth results
                          (- (int (/ x step)) 2))))))))