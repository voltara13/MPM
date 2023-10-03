(ns clojure_1_3)

(defn my-map [f coll]
  (reduce
   (fn [result element]
     (conj result (f element)))
   []
   coll))

(defn my-filter [pred coll]
  (reduce (fn [acc x]
            (if (pred x)
              (conj acc x)
              acc))
          []
          coll))