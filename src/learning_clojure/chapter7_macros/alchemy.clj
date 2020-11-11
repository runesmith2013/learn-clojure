;;MACROS
;;======

;; transform arbitrary expressions into valid clojure
;; can extend language as fits your needs

;; macro expansion phase happens between reading and evaluation. Macros allow us to transform an arbitraty data structure into one tha clojure can eval

(defmacro backwards
  [form]
  (reverse form))

(backwards (" backwards" " am " " I" str))


(def addition-list (list + 1 2))
(eval addition-list)

(defmacro ignore-last-operand
  [function-call]
  (butlast function-call))

(ignore-last-operand (+ 1 2 10))
(ignore-last-operand (+ 1 2 (println "look at me!!!")))

(defmacro infix
  [infixed]
  (list (second infixed)
        (first infixed)
        (last infixed)))

(infix (1 + 2))


;; threading or stabby macro ->

(defn read-resource
  [path]
  (-> path
      clojure.java.io/resource
      slurp
      read-string))

;; pipeline goes from top to bottom instead of inner to outer
;; lets us omit parentheses


(defmacro ignore-last-operand [function-call]
  (butlast function-call))

(ignore-last-operand (+ 1 2 10))

(macroexpand '(ignore-last-operand (+ 1 2 10)))