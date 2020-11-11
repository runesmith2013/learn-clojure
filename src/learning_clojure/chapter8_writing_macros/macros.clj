;; when is implemented as a macro

(macroexpand '(when boolean-expression
                  expression-1
                  expression-2
                  expression-3))

(defmacro infix2
  "Use this macro"
  [infixed]
  (list (second infixed) (first infixed) (last infixed)))

(infix (1 + 1))

;; argument destructuring with macros!
(defmacro infix-2
  [[op1 op op2]]
  (list op op1 op2))

;; naive macro to print and return
(defmacro my-print-whoopsie
  [expression]
  (list let [result expression]
        (list println result)
        result))

;; macro with quoting
(defmacro my-print
  [expression]
  (list 'let ['result expression]
        (list 'println 'result)
        'result))

(defmacro code-critic
  "Hermes phrases"
  [bad good]
  (list 'do
        (list 'println
              "Great squid of Madrid this is bad code:"
              (list 'quote bad))
        (list 'println
              ("Sweet gorilla of manilla this is good code:"
                (list 'quote good)))))

;; with syntax quoting

(defmacro code-critic-2
  "Hermes 2"
  [bad good]
  `(do (println "Great: " (quote ~bad))
       (println "Sweet: " (quote ~good))))

; some sweet sweet refactoring

(defn criticize-code
  [criticism code]
  `(println ~criticism (quote ~code)))

(defmacro code-critic-3
  [bad good]
  `(do ~(criticize-code "Bad " bad)
       ~(criticize-code "Good" good)))

;; now with maps!
(defmacro code-critic-4
  [bad good]
  `(do ~(map #(apply criticize-code %
                     [["Great " bad]
                      ["Sweet" good]]))))

;; unquote splicing: ~@
`(+ ~(list 1 2 3))
`(+ ~@(list 1 2 3))


(defmacro code-critic
  [{:keys [good bad]}]
  `(do ~@(map #(apply criticize-code %)
              [["Sweet lion of Zion, this is bad code:" bad]
               ["Great cow of Moscow, this is good code:" good]])))


;; validation functions

(def order-etails
  {:name "someone"
   :email "blamgmail.com"})

(def order-details-validations
  {:name ["Please enter a name" not-empty]
   :email ["Please enter an email address " not-empty

           "invalid email address"
           #(or (empty? %) (re-seq #"@" %))]})

(defn error-messages-for
  "Return a seq of error messages"
  [to-validate message-validator-pairs]                     ; to-validate is field to validate. message-validator-pairs is seq with even number of elements
  (map first (filter #(not (( second %) to-validate))       ;; use map to get the first element of each pair
                     (partition 2 message-validator-pairs)))) ;; group into pairs. first ele is error message, second is function


(error-messages-for "" ["Please enter a name " not-empty])


;; full validations function
(defn validate
  "Returns a map with a vector of errors for each key"
  [to-validate validations]
  (reduce (fn [errors validation]
            (let [[fieldname validation-check-groups] validation
                  value (get to-validate fieldname)
                  error-messages (error-messages-for value validation-check-groups)]
              (if (empty? error-messages)
                errors
                (assoc errors fieldname error-messages))))
          {}
          validations))

(validate order-details order-details-validations)


(let [errors (validate order-details order-details-validations)]
  (if (empty? errors)
    (println :success)
    (println :failure errors)))