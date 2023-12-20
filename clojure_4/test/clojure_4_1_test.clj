(ns clojure_4_1_test
  (:require [clojure.test :refer [deftest is run-tests]]
            [clojure_4_1 :refer :all]))

; Test cases for combine-same-args function
(deftest combine-same-args-test
  (is (= [(variable :x) (variable :y)] (combine-same-args (logic-or (variable :x) (variable :y)))))
  (is (= [(variable :x) (variable :y) (variable :z)] (combine-same-args (logic-or (variable :x) (logic-or (variable :y) (variable :z))))))
  (is (= [(variable :x) (variable :y) (variable :z)] (combine-same-args (logic-and (variable :x) (logic-and (variable :y) (variable :z)))))))

; Test cases for expr-to-str function
(deftest expr-to-str-test
  (is (= "((-x) | y)" (expr-to-str (dnf (logic-impl (variable :x) (variable :y))))))
  (is (= "y" (expr-to-str (dnf (logic-impl (constant 1) (variable :y))))))
  (is (= "((-x) | y)" (expr-to-str (dnf (logic-impl (constant 1) (logic-impl (variable :x) (variable :y)))))))
  (is (= "(x | y | z)" (expr-to-str (dnf (logic-or (variable :x) (logic-or (variable :y) (variable :z)))))))
  (is (= "((x & (-y)) | (x & (-y) & z))" (expr-to-str (dnf (logic-not (logic-or (logic-impl (variable :x) (variable :y)) (logic-not (logic-impl (variable :y) (variable :z))))))))))
  
; Test cases for impl-elimination function
(deftest impl-elimination-test
  (is (= (logic-or (logic-not (variable :x)) (variable :y)) (impl-elimination (logic-impl (variable :x) (variable :y))))))

; Test cases for negation-equivalencies function
(deftest negation-equivalencies-test
  (is (= (logic-and (logic-not (variable :x)) (logic-not (variable :y))) (negation-equivalencies (logic-not (logic-or (variable :x) (variable :y))))))
  (is (= (constant 0) (negation-equivalencies (logic-not (constant 1))))))

; Test cases for absorption-law function
(deftest absorption-law-test
  (is (= (variable :x) (absorption-law (logic-or (variable :x) (logic-and (variable :x) (variable :y))))))
  (is (= (variable :x) (absorption-law (logic-and (variable :x) (logic-or (variable :x) (variable :y)))))))

; Test cases for distributive-law function
(deftest distributive-law-test
  (is (= (logic-or (logic-and (variable :z) (variable :x)) (logic-and (variable :z) (variable :y))) 
             (distributive-law (logic-and (logic-or (variable :x) (variable :y)) (variable :z))))))

; Test cases for decompose function
(deftest decompose-test
  (is (= (logic-and (variable :x) (variable :y) (variable :z)) (decompose (logic-and (variable :x) (variable :y) (variable :z))))
  (is (= (logic-or (variable :x) (variable :y) (variable :z)) (decompose (logic-or (variable :x) (variable :y) (variable :z)))))))

; Test cases for idempotent-law function
(deftest idempotent-law-test
  (is (= (variable :x) (idempotent-law (logic-and (variable :x) (variable :x)))))
  (is (= (variable :x) (idempotent-law (logic-or (variable :x) (variable :x))))))

; Test cases for identity-and-domination-laws function
(deftest identity-and-domination-laws-test
  (is (= (constant 0) (identity-and-domination-laws (logic-and (variable :x) (constant 0)))))
  (is (= (variable :x) (identity-and-domination-laws (logic-and (variable :x) (constant 1)))))
  (is (= (constant 1) (identity-and-domination-laws (logic-or (variable :x) (constant 1)))))
  (is (= (variable :x) (identity-and-domination-laws (logic-or (variable :x) (constant 0))))))

; Test cases for dnf function
(deftest dnf-test
  (is (= (variable :x) (dnf (variable :x))))
  (is (= (logic-and (variable :x) (variable :y)) (dnf (logic-and (variable :x) (variable :y)))))
  (is (= (logic-or (logic-and (variable :x) (variable :y)) (logic-and (variable :x) (variable :z))) 
         (dnf (logic-and (variable :x) (logic-or (variable :y) (logic-not (logic-not (variable :z)))))))))

; Run all tests
(run-tests)