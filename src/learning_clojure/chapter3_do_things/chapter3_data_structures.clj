;; all data structures are immutable, so can't change them in place

;;Numbers
;;=======
93                                                          ;; int
1.2                                                         ;; float
1/5                                                         ;; ratio

;; Strings
;;========
;; only ", not '
;; no string interpolation. use the str function
"Lord Voldemort"
"\"He who must not be named\""
"\"Great cow of Moscow!\" - Hermes Conrad"

(def name "Chewie")
(str "uggg" "-" name)


;; Maps - hashmap and sortedmap
;======
{}                                                          ;; empty map
{:first-name "charlie"
 :last-name "boy"}                                          ;; first-name and last-name are keywords
{"string-key" +}                                            ;; associate string-key with + function
{:name {:first "John" :middle "Jacob" :last "Smith"}}       ;; nested map

(hash-map :a 1 :b 2)                                        ;; use hashmap function to create maps
(get {:a 1 :b 2} :b)                                        ;; lookup using get function
(get {:a 0 :b {:c "ho hum"}} :b)
(get {:a 0 :b 1} :c)                                        ;; return nil if no value found
(get-in {:a 0 :b {:c "ho hum"}} [:b :c])                    ;; look up values in nested maps
({:name "The human coffeepot"} :name)                       ;; treat the map like a function with key as argument

;; Keywords
;;=========
;; :a
;; :34

; can be used as functions to look up corresponding value in a data structure
(:a {:a 1 :b 2 :c 3})                                       ;; returns 1
(get {:a 1 :b 2 :c 3} :a)                                   ;; equivalent

(:d {:a 1 :b 2 :c 3} "Something else entirely")             ;; default values

;; Vectors
;;========
; similar to an array, in that its a zero-index collection
[3 2 1]
(get [3 2 1] 0)                                             ;; return 0th element
(get ["a" {:name "Pugsley Winterbottom"} "c"] 1)            ;; can mix types
(vector "creepy" "full" "moon")                             ;; use vector keyword
(conj [1 2 3] 4)                                            ;; use conj to add elements to vector

;; Lists
;; =====
; Linear collection of values, can't use get
'(1 2 3 4)                                                  ;; elements in parens, single quote at beginning
(nth '(:a :b :c) 0)                                         ;; nth function to retrieve
(nth '(:a :b :c) 2)
(list 1 "two" {3 4})                                        ;; list function, any types
(conj '(1 2 3) 4)                                           ;; add to front of the list

;; Sets
;; ====
; collection of unique values
#{"Kurt" 20 :icicle}                                        ;; literal notation
(hash-set 1 1 2 2)                                          ;; hash-set function
(conj #{:a :b} :b)                                          ;; use conj
(set [3 3 3 4 4])                                           ;; create set from lists and vectors
(contains? #{:a :b} :a)                                     ;; check for membership
(:a #{:a :b})                                               ;; using keywords
(get #{:a :b} :a)                                           ;; using get