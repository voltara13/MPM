(ns clojure_3_1
  (:require [clojure_1_3]))

(defn custom-partition [size coll]
  (if (empty? coll)
    '()
    (cons (take size coll) (custom-partition size (drop size coll)))))

(defn parallel-filter [pred coll block-size]
  (->> coll
       (custom-partition block-size)
       (map #(future (clojure_1_3/my-filter pred %)))
       (doall)
       (map deref)
       (apply concat)))