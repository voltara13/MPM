(ns clojure_3_2
  (:require [clojure_1_3]))

(def lazy-block-size 10)

(defn parallel-filter [pred coll block-size]
  (->> coll
       (partition-all lazy-block-size)
       (map #(partition-all block-size %))
       (map (fn filter-lazy-block [lazy-block]
              (->> lazy-block
                   (map #(future (clojure_1_3/my-filter pred %)))
                   (doall)
                   (mapcat deref))))
       (apply concat)))