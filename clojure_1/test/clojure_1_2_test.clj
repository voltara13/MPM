(ns clojure_1_2_test
  (:require [clojure.test :refer [deftest is run-tests]]
            [clojure_1_2 :as src]))

(deftest test-get-all-str
  (is (= ["cbcb" "cbca" "cbac" "cbab" "cacb" "caca" "cabc" "caba" 
          "bcbc" "bcba" "bcac" "bcab" "bacb" "baca" "babc" "baba" 
          "acbc" "acba" "acac" "acab" "abcb" "abca" "abac" "abab"] (src/get-all-str 4 ["a" "b" "c"])))
  (is (= ["cbc" "cba" "cac" "cab" 
          "bcb" "bca" "bac" "bab" 
          "acb" "aca" "abc" "aba"]                                 (src/get-all-str 3 ["a" "b" "c"])))
  (is (= ["cb" "ca"
          "bc" "ba"
          "ac" "ab"]                                               (src/get-all-str 2 ["a" "b" "c"])))
  (is (= ["baba" "abab"]                                           (src/get-all-str 4 ["a" "b"])))
  (is (= ["bab" "aba"]                                             (src/get-all-str 3 ["a" "b"])))
  (is (= ["ba" "ab"]                                               (src/get-all-str 2 ["a" "b"])))
  (is (= ["b" "a"]                                                 (src/get-all-str 1 ["a" "b"])))
  (is (= ["a"]                                                     (src/get-all-str 1 ["a"])))
  (is (= []                                                        (src/get-all-str 2 ["a"])))
  (is (= []                                                        (src/get-all-str 2 ["a" "a"])))
  (is (= []                                                        (src/get-all-str -3 ["a" "b"])))
  (is (= []                                                        (src/get-all-str 2 []))))

(defn -main []
  (run-tests 'clojure_1_2_test))

(-main)
