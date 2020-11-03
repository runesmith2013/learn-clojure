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

(reduce (fn [new-map [key val]]
          (assoc new-map key (inc val)))
        {}
        {:max 30 :min 10})
; reduce treats the arguments {:max 30 :min 10} as a sequence of vectors like ([:max 30] [:min 10]}
; then starts with the empty map and builds it up using the first argument, the anonymous function
; its as if reduce does this:
(assoc (assoc {} :max (inc 30))                             ; assoc takes 3 args: map, key, value and derives new map by associating key and value
  :min (inc 10))


; reduce can filter out keys from a map based on their value
(reduce (fn [new-map [key val]]
          (if (> val 4)
            (assoc new-map key val)
            new-map))
        {}
        {:human 4.1
         :critter 3.9})

;; TAKE, DROP, TAKE-WHILE, DROP-WHILE
;;===================================
(take 3 [1 2 3 4 5 6 7])
(drop 3 [1 2 3 4 5 6 7])

; take-while and drop-while take a predicate to determine when to stop taking or dropping

(def food-journal
  [{:month 1 :day 1 :human 5.3 :critter 2.3}
   {:month 1 :day 2 :human 5.1 :critter 2.0}
   {:month 2 :day 1 :human 4.9 :critter 2.1}
   {:month 2 :day 2 :human 5.0 :critter 2.5}
   {:month 3 :day 1 :human 4.2 :critter 3.3}
   {:month 3 :day 2 :human 4.0 :critter 3.8}
   {:month 4 :day 1 :human 3.7 :critter 3.9}
   {:month 4 :day 2 :human 3.7 :critter 3.6}])

(take-while #( < (:month %) 3) food-journal)
(drop-while #( < (:month %) 3) food-journal)

; combining our powers!
; use drop-while to get rid of jan, then pass result to take-while to take until April
(take-while #(< (:month %) 4) (drop-while #(< (:month %) 2) food-journal))

; Filter to return all elements of seq that is true for predicate
(filter #(< (:human %) 5) food-journal)

; some to check whether collection contains a value
(some #(> (:critter %) 5) food-journal)                     ; return nil
(some #(> (:critter %) 3) food-journal)                     ; return true

(some #(and (> (:critter %) 3) %) food-journal)


;; Sort and sort-by
(sort [3 2 1])
(sort-by count ["aaa" "c" "bb"])

;; concat - appends one seq to another
(concat [1 2] [3 4])

;; LAZY SEQUENCES

(def vampire-database
  {0 {:makes-blood-puns? false, :has-pulse? true  :name "McFishwich"}
   1 {:makes-blood-puns? false, :has-pulse? true  :name "McMackson"}
   2 {:makes-blood-puns? true,  :has-pulse? false :name "Damon Salvatore"}
   3 {:makes-blood-puns? true,  :has-pulse? true  :name "Mickey Mouse"}})

; takes one sec to look up entry from database
(defn vampire-related-details
  [social-security-number]
  (Thread/sleep 1000)
  (get vampire-database social-security-number))

; return record if is vampire, else false
(defn vampire?
  [record]
  (and (:makes-blood-puns? record)
       (not (:has-pulse? record))
       record))

; map social security numbers to database records and returns first record that is true
(defn identify-vampire
  [social-security-numbers]
  (first (filter vampire?
                (map vampire-related-details social-security-numbers))))

(time (vampire-related-details 0))
(time (def mapped-details (map vampire-related-details (range 0 1000000))))


;; INFINITE SEQUENCES
(concat (take 8 (repeat "na")) ["Batman"])                  ;; repeat generates infinite sequence, but lazily

(take 3 (repeatedly (fn [] (rand-int 10))))                 ;; repeatedly calls provided function

(defn even-numbers
  ([] (even-numbers 0))
  ([n] (cons n (lazy-seq (even-numbers (+ n 2))))))

(take 10 (even-numbers))