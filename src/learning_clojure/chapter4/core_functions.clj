; programming to abstractions
; abstractions are sequences of operations. If you can perform all of an abstractions operations then it is an instance => duck typing

; if core sequence first rest and cons work on a data structure then it implements the sequence abstraction
; this covers lists, vectors, set and maps

; functions are implemented in terms of data structure abstractions
; map, reduce take a sequence

(defn titleize
  [topic]
  (str topic " for the brave and true"))

(map titleize ["Hamsters" "Ragnarok"])
(map titleize '("Empathy" "Decorating"))
(map titleize #{"Elbows" "Soap Carving"})
(map #(titleize (second %)) {:uncomfortable-thing "Winking"})

;; SEQ function examples

;; MAP
;; ---
(map inc [1 2 3 4])                                         ; single collection
(map str ["a" "b" "c"] ["A" "B" "C"] )                      ; multiple collections

(def human-consumption [8.1 7.3 3.6])
(def critter-consumption [0.0 0.2 0.3])
(defn unify
  [human critter]
  {:human human
   :critter critter})

(map unify human-consumption critter-consumption)

; pass collection of functions to map
(def sum #(reduce + %))                                     ; sum applies + to all args
(def avg #(/ (sum %) (count %)))                            ; sum, then count, then /
(defn stats
  [numbers]
  (map #(% numbers) [sum count avg]))

(stats [3 4 10])
(stats [80 1 44 13 6])

(def identities                                             ; use map to retrieve value associated with keyword from a collection of maps
  [{:alias "Batman" :real "Bruce Wayne"}
   {:alias "Spiderman" :real "Peter Parker"}])
(map :real identities)

;;Reduce
;;======
;; processes each element to build a result

; transform map values, producing a new map with updated values

(reduce (fn [new-map [key val]]))