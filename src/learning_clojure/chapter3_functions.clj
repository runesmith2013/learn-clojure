(+ 1 2 3 4)
(* 1 2 3 4)
(first [1 2 3 4])

(or + -)                                                    ;; returns the first truthy value
((or + -) 1 2 3)                                            ;; returns 6
((and (= 1 1) +) 1 2 3)                                     ;; and returns first false or last truth, so plus, returns 6
((first [+ 0]) 1 2 3)                                       ;; first element of vector then applied to args

(1 2 3 4)                                                   ;; invalid function call
("test" 1 2 3 4)                                            ;; strings aren't functions

;; higher order functions
;;=======================
; functions that can take function as arg or return a function
(inc 1.1)
(map inc [0 1 2 3])                                         ;; map applies function to each element of a collection


;; defining functions
;--------------------
;; defn
;; function name
;; doc-string describing function
;; parameters listed in brackets
;; function body

(defn too-enthusiastic
  "Return a cheer thats over the top"
  [name]
  (str "oh. my. god " name "  you are the best!"))

(too-enthusiastic "Zelda")

;; can view docsting for a function with (doc fn-name)
(doc too-enthusiastic)

;; parameters and arity
(defn no-params
  []
  "I take no params!")
(defn one-param
  [x]
  (str "I take one param!" x))
(defn two-params
  [x y]
  (str "I will smoosh them" x y))
(one-param "hello")
(two-params "hello" "world")

;; multi-artity functions
(defn multi-arity
  ;; 3-arity
  ([first second third]
   (do-things first second third))
  ;; 2-arity
  ([first second])
  (do-things first second))
;; 1-arity
 ([first]
  (do-things first))

;; provide default values with multi-arity
(defn x-chop
  "Describe the kind of chop you're inflicting on someone"
  ([name chop-type]
   (str "I " chop-type " chop " name "! Take that!"))
  ([name]
   (x-chop name "karate")))

;; variable arity using &
(defn codger-comms
  [whippersnapper]
  (str "Get off my lawn, " whippersnapper))

(defn codger
  [& whippersnappers]
  (map codger-comms whippersnappers))

(codger "Billy" "Anne" "Freddy")

