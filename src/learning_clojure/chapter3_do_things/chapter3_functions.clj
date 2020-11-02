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

;; mix rest parameters with normal parameters
(defn favorite-things
  [name & things]
  (str "Hi, " name ", here are my favorite things:"
       (clojure.string/join "," things)))

(favorite-things "Doreen" "gum" "shoes" "kara-te")

;; destructuring
;;--------------
;; allows you to bind names to values withing a collection
(defn my-first
  [[first-thing]]
  first-thing)                                          ; first-thing is within a vector

(my-first ["oven" "bike" "war-axe"])                        ;; associates first-thing with first element of vector

(defn chooser
  [[ first-choice second-choice & unimportane-choices]]
  (println (str "your first choice is " first-choice))
  (println (str "Your second choice is " second-choice ))
  (println (str "Unimportant choices:"
                (clojure.string/join "," unimportane-choices))))

(chooser ["Marmalade" "Jack" "Pigpen" "Aquaman"])

; destructuring maps
(defn announce-treasure-location
  [{lat :lat lng :lng}]                                     ;; associate name lat with value corresponding to key :lat
  (println (str "Treasure lat: " lat))
  (println (str "Treasure lng: " lng)))

(announce-treasure-location
  {:lat 28.22 :lng 81.33})

; shorter version
(defn announce-treasure-location
  [{:keys [lat lng]}]
  (println (str "Treasure lat: " lat))
  (println (str "Treasure lng: " lng)))

(announce-treasure-location
  {:lat 28.22 :lng 81.33})


;; Function Body
;===============
; Can contain forms of any kind
; clojure automatically returns last form evaluated

(defn illustrative-function
  []
  (+ 1 304)
  30
  "joe")

(illustrative-function)                                     ;; spits out joe


;; with an if expression
(defn number-comment
  [x]
  (if (> x 6)
    "Oh my gosh"
    "wow"))

(number-comment 5)
(number-comment 7)


;; anonymous functions
;;====================
; functions don't need to have names
(fn [param-list]
  function body)

(map (fn [name] (str "Hi, " name))
     ["Darth Vader" "Mr Magoo"])                            ;; define anonymous function as parameter to map, along with vector of arguments

((fn [x] (* x 3)) 8)                                        ;; anonymous function as first argument, in line definition

(def my-special-multiplier (fn [x] (* x 3)))                ;; associate with name using def
(my-special-multiplier 12)

;; compact anonymous functions
;;----------------------------
#(* % 3)
(#(* % 3) 8)

(map #(str "Hi " %)                                         ;; % indicates argument to anon function. %1, %2, %3 etc
     ["Darth" "Magoo"])

(#(str %1 " and " %2) "cornbread" "butter")                 ;; %1 -> first arg, %2 -> second arg
(#(identity %&) 1 "blarg" :yip)                             ;; %& -> rest params


;; Returning functions
;;====================
; returned functions are closures. they have access to variables in scope when fn created
(defn inc-maker
  "Create a custom incrementor"
  [inc-by]
  #(+ % inc-by))

(def inc3 (inc-maker 3))

(inc3 7)