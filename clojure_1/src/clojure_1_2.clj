(ns clojure_1_2)

(defn get-all-str
  "Get a list of all strings of length n,
   consisting of characters from the alphabet list
   and not containing two identical characters in a row."
  [n alphabet]
  (letfn [(add-to-result [current-string]
            (if (= (count current-string) n)
              [current-string]
              (letfn [(get-str-variant [letters]
                        (when (not-empty letters)
                          (let [letter (first letters)]
                            (if (or (= (count current-string) 0)
                                    (not= (last current-string) letter))
                              (concat
                               (add-to-result (str current-string letter))
                               (get-str-variant (rest letters)))
                              (recur (rest letters))))))]
                (get-str-variant alphabet))))]
    (cond
      (<= n 0) []
      (empty? alphabet) []
      :else
      (add-to-result ""))))