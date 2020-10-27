;; Forms
;; =====
;; - Literal representations
;; - Operations

;; Operations
;; ==========
;; (operator op1 op2 .. opn)
;; no commas, whitespace to separate operands
 (+ 1 2 3)
 (str "It was " "the panda")

;; Control Flow
;; ============

;; if
;;---
(if true
  "By zeus' hammer"                                         ;; eval if true
  "By aquaman's trident")                                   ;; eval if false

(if false
  "By zeus' hammer"                                         ;; eval if true
  "By aquaman's trident")                                   ;; eval if false

(if false
  "By Odin's elbow!")                                       ;; return nil


;; do - group ops together
;;---
(if true
  (do (println "Success!")
      "By Zeus' Hammer!")
  (do (println "Failure!")
      "By Aquaman's trident!")
  )

;; when - combination of if and do, with no else block
;------
(when true
  (println "Success!")
  "abracadabra!")

;; nil ==> no value. check if nil with nil function
;;----
(nil? 1)
(nil? nil)

;; nil and false are false, all others are true
(if "bears"                                                 ;; evaluates to true
  "Battlestar")

(if nil                                                     ;; evaluates to false
  "Not this"
  "This")

;; Equality operator is =. Valid for every type and object
;; --------
(= 1 1)
(= nil nil)
(= 1 2)

;; Boolean operators
;===================

;; Or - returns first truthy value or last value
(or false nil :large :venti)                                ;; returns large
(or (= 0 1) (= "yes" "no"))                                 ;; returns false
(or nil)                                                    ;; returns nil

;; and - returns last falsey value or  last value
(and :free_wifi :hot_coffee)                                ;; hot_coffee
(and :feelin nil false)                                     ;; nil, first falsey value


;; Naming Values
;===============
; use def to bind a name to a value.  treat def as creating constants. Clojure does not like changing values
(def failed-hero-names
  ["Larry Potter" "Doreen the exploder" "The incredible sulk"]) ;; [] indicates a vector containing three strings?

failed-hero-names                                           ;; prints out names
