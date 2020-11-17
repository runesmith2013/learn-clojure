;; multimethods
;;=============

;;associate a name with multiple implementations by using a dispatch function which produces results used to determine which method to use
(ns were-creatures)

(defmulti full-moon-behaviour (fn [were-creature] (:were-type were-creature)))
(defmethod full-moon-behaviour :wolf
  [were-creature]
  (str (:name were-creature) "I will howl"))
(defmethod full-moon-behaviour :simmons
  [were-creature]
  (str (:name were-creature) "I will dance"))

(defmethod full-moon-behaviour nil
  [were-creature]
  (str (:name were-creature) " will stay at home"))

(defmethod full-moon-behaviour :default
  [were-creature]
  (str (:name were-creature) " will eat ice cream"))


;; other poeple can continue extending the multimethod to handle new disptahc values
(ns random-namespace
  (:require [were-creatures]))

(defmethod were-creatures/full-moon-behaviour :bill-murray
  [were-creature]
  (str (:name were-creature) " will be awesome"))

;; dispatching function can return arbitrary values using any or all of its args
(ns user)
(defmulti types (fn [x y] [(class x) (class y)]))
(defmethod types [java.lang.String java.lang.String]
  [x y]
  "Two strings!")


;; PROTOCOLS
;;==========

; optimised for type dispatch
; protocol is a collection of one or more operations
; dispatched based on type of first argument

; defines an abstraction

(ns data-psychology)
(defprotocol Psychodynamics
  "Some description"
  (thoughts [x] "innermost thoughts")                       ; method signatures
  (feelings [x] [x y] "Feelings about self and others"))    ; methods can't have rest arguments

; extend the string data type to implement the protocol
(extend-type java.lang.String
  Psychodynamics
  (thoughts [x] (str x " thinks about stuff"))
  (feelings
    ([x] (str x " is longing for a simpler way"))
    ([x y] (str x "is jealous of " y))))


;; extend java.lang.Object to get a default behaviour
(extend-type java.lang.Object
  Psychodynamics
  (thoughts [x] "Maybe the Internet is just a vector for toxoplasmosis")
  (feelings
    ([x] "meh")
    ([x y] (str "meh about " y))))

;; can extend multiple types at once using extend-protocol!
(extend-protocol Psychodynamics
  java.lang.String
  (thoughts [x] "Truly, the character defines the data type")
  (feelings-about
    ([x] "longing for a simpler way of life")
    ([x y] (str "envious of " y "'s simpler way of life")))

  java.lang.Object
  (thoughts [x] "Maybe the Internet is just a vector for toxoplasmosis")
  (feelings-about
    ([x] "meh")
    ([x y] (str "meh about " y))))

