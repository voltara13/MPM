(ns clojure_1_2_3)

(defn get-all-str
  "Get a list of all strings of length n,
   consisting of characters from the alphabet list
   and not containing two identical characters in a row."
  [n alphabet]
  (if (<= n 0)
    []
    (loop [[current-string & stack] [""]
           result []]
      (if current-string
        (if (= n (count current-string))
          (recur stack (conj result current-string))
          (recur (concat stack
                         (loop [acc []
                                remaining (remove #(= % (last current-string)) alphabet)]
                           (if (empty? remaining)
                             acc
                             (recur ((fn [result element]
                                       (conj result ((partial str current-string) element)))
                                     acc (first remaining)) (rest remaining)))))
                 result))
        result))))