(ns clojure_1_2_2)

(defn get-all-str
  "Get a list of all strings of length n,
     consisting of characters from the alphabet list
     and not containing two identical characters in a row."
  ([n alphabet]
   (if (<= n 0)
     []
     (loop [n n
            result alphabet]
       (if (> n 1)
         (recur (dec n)
                (loop [string-list result
                       result []]
                  (if (not-empty string-list)
                    (recur (rest string-list)
                           (concat result
                                   (loop [added-string alphabet
                                          result []]
                                     (if (not-empty added-string)
                                       (recur (rest added-string)
                                              (if (not= (last (first string-list)) (first added-string))
                                                (concat result
                                                        (list (str (first string-list)
                                                                   (first added-string))))
                                                result))
                                       result))))
                    result)))
         result)))))