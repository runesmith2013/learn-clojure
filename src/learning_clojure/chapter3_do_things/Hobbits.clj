; define a vector of maps
; each map has name of body part and its size
(def asym-hobbit-body-parts [{:name "head" :size 3}
                             {:name "left-eye" :size 1}
                             {:name "left-ear" :size 1}
                             {:name "mouth" :size 1}
                             {:name "nose" :size 1}
                             {:name "neck" :size 1}
                             {:name "left-shoulder" :size 3}
                             {:name "left-upper-arm" :size 3}
                             {:name "chest" :size 10}
                             {:name "back" :size 10}
                             {:name "left-forearm" :size 3}
                            {:name "abdomen" :size 6}
                              {:name "left-kidney" :size 1}
                              {:name "left-hand" :size 2}
                              {:name "left-knee" :size 2}
                              {:name "left-thigh" :size 4}
                              {:name "left-lower-leg" :size 3}
                              {:name "left-achilles" :size 1}
                              {:name "left-foot" :size 2}])

(defn matching-part
   [part]
   {:name (clojure.string/replace (:name part) #"^left-" "right-")
   :size (:size part)}
)

(defn symmetrize-body-parts
"expects a seq of body parts that have a :name and :size"
[asym-body-parts]
(loop [remaining-asym-parts asym-body-parts final-body-parts []]
 (if (empty? remaining-asym-parts)
   final-body-parts
   (let [[part & remaining] remaining-asym-parts];; let binds names to values
     (recur remaining
       (into final-body-parts
         (set [part (matching-part part)])))))))

(symmetrize-body-parts asym-hobbit-body-parts)

;; let
; binds names to values
(let [x 3];; bind name x to value 3
x)

(def dalmatian-list
 ["Pongo" "Perdita" "Puppy1" "Puppy 2"])
 (let [dalmatians (take 2 dalmatian-list)] ;; bind dalmatians to result of take 2 expression
  dalmatians)

; let introduces a new scope
(def x 0) ;; bind x to 0 globally
(let [x 1] x) ;; bind x to 1 in context of this expression

; can reference existing bindings
(def x 0)
(let [x (inc x)] x)

(let [[pongo & dalmatians] dalmatian-list] ;; [pongo & dalmatians] destructured the dalmation list, binding "Pongo" to pong and the list of the rest to dalmatians
[pongo dalmatians]) ;; last expression, so its the return value


;; Create a new scope, within it associate part with first element and remaining with other elements
(let [[part & remaining] remaining-asym-parts]
  (recur remaining
         (into final-body-parts                          ;;
               (set [part (matching-part part)]))))    ;;
;; create a set consisting of part and its matching part, then use function into to add the elements into final-body-parts vector

(into [] (set [:a :a]))
;; (set [:a :a]) returns set #{:a}
;; (into [] #{a}) returns the vector [:a]


;; LOOP
;;=====
(loop [iteration 0]                       ;; begins loop and introduces binding with initial value
  (println (str "Iteration " iteration))
  (if (> iteration 3)
    (println "Goodbye!")
    (recur (inc iteration))))

; recursive without loop
(defn recursive-printer
([]
  (recursive-printer 0))
  ([iteration]
    (println iteration)
    (if (> iteration 3)
      (println "Goodbye")
      (recursive-printer (inc iteration)))))

(recursive-printer)


;; regular expressions
;;====================
; literal notation is to place expression in quotation marks after # .. #"reg-expr"
(re-find #"^left-" "left-eye")  ;; ^indicates match at start of word
(re-find #"^left-" "cleft-chin")

(defn matching-part
 [part]
 {:name (clojure.string/replace (:name part) #"^left-" "right-")
   :size (:size part) })

(matching-part {:name "left-eye" :size 1})
 (matching-part {:name "head" :size 3})

 ;; Reduce
 ;--------
 ; process each element and build a result
 (reduce + [1 2 3 4])

(+ (+ (+ 1 2) 3) 4)
(apply + [1 2 3 4])

;; reducer implementation
(defn my-reduce
([f initial coll]
 (loop [result initial remaining coll]
   (if (empty? remaining)
    result
    (recur (f result (first remaining)) (rest remaining)))))
([f [head & tail]]
 (my-reduce f head tail))))


; symmetrizer using reduce
(defn better-symmetrize-body-parts
  "Expects a seq of maps that have a :name and :size"
  [asym-body-parts]
  (reduce (fn [final-body-parts part]
            (into final-body-parts (set [part (matching-part part)])))
          []
          asym-body-parts))


(defn hit
  [asym-body-parts]
  (let [sym-parts (better-symmetrize-body-parts asym-body-parts) ; symmetrize body parts, bind to sym-parts
        body-part-size-sum (reduce + (map :size sym-parts)) ; find size of each sym part, then reduce using +, assign to body-part-size
        target (rand body-part-size-sum)] ; bind target to random number below size sum
    (loop [[part & remaining] sym-parts   ; destructure sym=parts into part and remaining
           accumulated-size (:size part)] ; set accumulated size to size value of part
      (if (> accumulated-size target)     ; if target > acc size
        part                              ; return part
        (recur remaining (+ accumulated-size (:size (first remaining))))))))  ; get first of remaining, look up size, add to accumulated size, call loop with remaining and accumulated size

