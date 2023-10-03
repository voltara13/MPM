(ns clojure_1_4)

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
                         (map (partial str current-string)
                              (remove #(= % (last current-string)) alphabet)))
                 result))
        result))))