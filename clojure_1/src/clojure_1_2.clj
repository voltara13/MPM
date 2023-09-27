(ns clojure_1_2)

(defn get-all-str
  "Get a list of all strings of length n,
   consisting of characters from the alphabet list
   and not containing two identical characters in a row."
  [n alphabet]
  (letfn [(add-to-result [current-string result]
            (if (= (count current-string) n)
              (conj result current-string)
              (loop [remaining-alphabet (remove #(= % (last current-string)) alphabet)
                     acc result]
                (if (empty? remaining-alphabet)
                  acc
                  (recur (rest remaining-alphabet)
                         (add-to-result (str current-string (first remaining-alphabet)) acc))))))]
    (if (<= n 0)
      []
      (add-to-result "" []))))