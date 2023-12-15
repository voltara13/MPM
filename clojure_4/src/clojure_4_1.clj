(ns clojure_4_1)

; CONSTRUCTORS

(defn constant
  "Constructor for constant"
  [value]
  {:pre [(or (= value 0) (= value 1))]}
  (list ::const value))

(defn variable
  "Constructor for variable"
  [name]
  {:pre [(keyword? name)]}
  (list ::var name))

(defn logic-not
  "Constructor for negation"
  [expr]
  (list ::not expr))

(defn logic-or
  "Constructor for disjunction"
  [expr & rest]
  (if (empty? rest)
    expr
    (cons ::or (cons expr rest))))

(defn logic-and
  "Constructor for conjunction"
  [expr & rest]
  (if (empty? rest)
    expr
    (cons ::and (cons expr rest))))

(defn logic-impl
  "Constructor for implication"
  [expr1 expr2]
  (list ::impl expr1 expr2))

; GETTERS

(defn constant-value
  "Get constant value"
  [expr]
  (second expr))

(defn variable-name
  "Get variable name"
  [var]
  (name (second var)))

(defn get-type
  "Get type of operation"
  [expr]
  (keyword (first expr)))

(defn args
  "Get arguments of operation"
  [expr]
  (rest expr))

(defn first-arg
  "Get first argument of operation"
  [expr]
  (first (rest expr)))

(defn second-arg
  "Get second argument of operation"
  [expr]
  (second (rest expr)))

; CHECKS

(defn constant?
  "Check if expr is constant"
  [expr]
  (= (first expr) ::const))

(defn variable?
  "Check if expr is variable"
  [expr]
  (= (first expr) ::var))

(defn logic-not?
  "Check if expression is negation"
  [expr]
  (= (first expr) ::not))

(defn logic-or?
  "Check if expression is disjunction"
  [expr]
  (= (first expr) ::or))

(defn logic-and?
  "Check if expression is conjunction"
  [expr]
  (= (first expr) ::and))

(defn logic-impl?
  "Check if expression is implication"
  [expr]
  (= (first expr) ::impl))

(defn same-type?
  "Check that expr1 and expr2 are of the same type"
  [expr1 expr2]
  (= (first expr1) (first expr2)))

; OPERATIONS

(defn combine-same-args
  "Combine arguments of the same type in one operation"
  [expr]
  (if (or (constant? expr) (variable? expr))
    (list expr)
    (->> (args expr)
         (mapcat #(if (same-type? expr %)
                    (combine-same-args %)
                    (list %))))))

(defn expr-to-str
  "Converting expression to string"
  [expr]
  (cond
    (constant? expr) (constant-value expr)
    (variable? expr) (variable-name expr)
    (logic-not? expr) (str "(" (str "-" (expr-to-str (first (args expr)))) ")")
    (logic-or? expr) (str "(" (reduce #(str %1 " | " %2)
                                      (expr-to-str (first (args expr)))
                                      (map expr-to-str (rest (args expr)))) ")")
    (logic-and? expr) (str "(" (reduce #(str %1 " & " %2)
                                       (expr-to-str (first (args expr)))
                                       (map expr-to-str (rest (args expr)))) ")")
    (logic-impl? expr) (str "(" (str (expr-to-str (first (args expr))) " -> " (expr-to-str (second (args expr)))) ")")))

(defn impl-elimination
  "x -> y = -x | y"
  [expr]
  (cond
    (logic-or? expr) (apply logic-or (map #(impl-elimination %) (args expr)))
    (logic-and? expr) (apply logic-and (map #(impl-elimination %) (args expr)))
    (logic-not? expr) (logic-not (impl-elimination (first-arg expr)))
    (logic-impl? expr) (logic-or (logic-not (impl-elimination (first-arg expr))) (impl-elimination (second-arg expr)))
    :else expr))

(defn negation-equivalencies
  "-(x | y) = -x & -y; -(x & y) = -x | -y
   -(-x) = x
   -0 = 1; -1 = 0"
  [expr]
  (cond
    (logic-not? expr) (let [sub-expr (first-arg expr)]
                        (cond
                          (logic-or? sub-expr) (apply logic-and (map #(negation-equivalencies (logic-not %)) (args sub-expr)))
                          (logic-and? sub-expr) (apply logic-or (map #(negation-equivalencies (logic-not %)) (args sub-expr)))
                          (logic-not? sub-expr) (negation-equivalencies (first-arg sub-expr))
                          (constant? sub-expr) (if (= sub-expr (constant 0)) (constant 1) (constant 0))
                          :else expr))
    (logic-or? expr) (apply logic-or (map #(negation-equivalencies %) (args expr)))
    (logic-and? expr) (apply logic-and (map #(negation-equivalencies %) (args expr)))
    :else expr))

(defn absorption-law
  "x | (x & y) = x; x & (x | y) = x"
  ([expr]
   (cond
     (logic-or? expr) (absorption-law logic-and? logic-or (first-arg expr) (second-arg expr))
     (logic-and? expr) (absorption-law logic-or? logic-and (first-arg expr) (second-arg expr))
     :else expr))
  ([inner-op-check outer-op sub-expr1 sub-expr2]
   (cond
     (inner-op-check sub-expr1) (if (or (= sub-expr2 (first-arg sub-expr1)) (= sub-expr2 (second-arg sub-expr1)))
                                  (absorption-law sub-expr2)
                                  (outer-op (absorption-law sub-expr1) (absorption-law sub-expr2)))
     (inner-op-check sub-expr2) (if (or (= sub-expr1 (first-arg sub-expr2)) (= sub-expr1 (second-arg sub-expr2)))
                                  (absorption-law sub-expr1)
                                  (outer-op (absorption-law sub-expr1) (absorption-law sub-expr2)))
     :else (outer-op (absorption-law sub-expr1) (absorption-law sub-expr2)))))

(defn distributive-law
  "(x | y) & z = (x & z) | (y & z)"
  [expr]
  (cond
    (logic-and? expr) (let [sub-expr1 (first-arg expr) sub-expr2 (second-arg expr)]
                        (cond
                          (logic-or? sub-expr1) (logic-or (distributive-law (logic-and sub-expr2 (first-arg sub-expr1)))
                                                          (distributive-law (logic-and sub-expr2 (second-arg sub-expr1))))
                          (logic-or? sub-expr2) (logic-or (distributive-law (logic-and sub-expr1 (first-arg sub-expr2)))
                                                          (distributive-law (logic-and sub-expr1 (second-arg sub-expr2))))
                          :else expr))
    (logic-or? expr) (apply logic-or (map #(distributive-law %) (args expr)))
    :else expr))

(defn decompose
  "x & (y & z) = x & y & z"
  [expr]
  (if (or (logic-or? expr) (logic-and? expr))
    (cons (get-type expr) (map #(decompose %) (combine-same-args expr)))
    expr))

(defn idempotent-law
  "x & x = x; x | x = x"
  [expr]
  (cond
    (logic-and? expr) (apply logic-and (map #(idempotent-law %) (distinct (args (decompose expr)))))
    (logic-or? expr) (apply logic-or (map #(idempotent-law %) (distinct (args (decompose expr)))))
    (logic-not? expr) (logic-not (idempotent-law (first-arg expr)))
    :else expr))

(defn identity-and-domination-laws
  "x & 0 = 0; x | 1 = 1
   x & 1 = x; x | 0 = x"
  ([expr]
   (let [expr (decompose expr)]
     (cond
       (logic-and? expr) (cond
                           (identity-and-domination-laws expr (constant 0)) (constant 0)
                           (identity-and-domination-laws expr (constant 1)) (identity-and-domination-laws (apply logic-and (filter #(not (= % (constant 1))) (args expr))))
                           :else (apply logic-and (map #(identity-and-domination-laws %) (args expr))))
       (logic-or? expr) (cond
                          (identity-and-domination-laws expr (constant 1)) (constant 1)
                          (identity-and-domination-laws expr (constant 0)) (identity-and-domination-laws (apply logic-or (filter #(not (= % (constant 0))) (args expr))))
                          :else (apply logic-or (map #(identity-and-domination-laws %) (args expr))))
       :else expr)))
  ([expr const]
   (some #(and (constant? %) (= const %)) (args expr))))

(defn dnf
  "Build DNF"
  [expr]
  (->> expr
       (impl-elimination)
       (negation-equivalencies)
       (distributive-law)
       (absorption-law)
       (idempotent-law)
       (identity-and-domination-laws)))

(println "x -> y =" (expr-to-str (dnf (logic-impl (variable :x) (variable :y)))))
(println "1 -> y =" (expr-to-str (dnf (logic-impl (constant 1) (variable :y)))))
(println "1 -> (x -> y) =" (expr-to-str (dnf (logic-impl (constant 1) (logic-impl (variable :x) (variable :y))))))
(println "x | y | z =" (expr-to-str (dnf (logic-or (variable :x) (logic-or (variable :y) (variable :z))))))
(println "-((x -> y) | -(y -> z)) =" (expr-to-str (dnf (logic-not (logic-or
                                                                   (logic-impl (variable :x) (variable :y))
                                                                   (logic-not (logic-impl (variable :y) (variable :z))))))))