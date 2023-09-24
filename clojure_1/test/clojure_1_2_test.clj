(ns clojure_1_2_test
  (:require [clojure.test :refer [deftest is run-tests]]
            [clojure_1_2 :as src]))

(deftest test-get-all-str
  (is (= ["abab" "abac" "abca" "abcb" "acab" "acac" "acba" "acbc"
          "baba" "babc" "baca" "bacb" "bcab" "bcac" "bcba" "bcbc"
          "caba" "cabc" "caca" "cacb" "cbab" "cbac" "cbca" "cbcb"] (src/get-all-str 4 ["a" "b" "c"])))
  (is (= ["aba" "abc" "aca" "acb"
          "bab" "bac" "bca" "bcb"
          "cab" "cac" "cba" "cbc"]                                 (src/get-all-str 3 ["a" "b" "c"])))
  (is (= ["ab" "ac"
          "ba" "bc"
          "ca" "cb"]                                               (src/get-all-str 2 ["a" "b" "c"])))
  (is (= ["abab" "baba"]                                           (src/get-all-str 4 ["a" "b"])))
  (is (= ["aba" "bab"]                                             (src/get-all-str 3 ["a" "b"])))
  (is (= ["ab" "ba"]                                               (src/get-all-str 2 ["a" "b"])))
  (is (= ["a" "b"]                                                 (src/get-all-str 1 ["a" "b"])))
  (is (= ["a"]                                                     (src/get-all-str 1 ["a"])))
  (is (= []                                                        (src/get-all-str 2 ["a"])))
  (is (= []                                                        (src/get-all-str 2 ["a" "a"])))
  (is (= []                                                        (src/get-all-str -3 ["a" "b"])))
  (is (= []                                                        (src/get-all-str 2 []))))

(defn -main []
  (run-tests 'clojure_1_2_test))

(-main)
