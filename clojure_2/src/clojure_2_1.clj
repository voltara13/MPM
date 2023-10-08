(ns clojure_2_1
  (:require [clojure_2_0 :as src]))

(def calc-integral-mem (memoize src/calc-integral))