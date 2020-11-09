;; pure functions
;;===============

; always same result with same input ==> referentially transparent
; no side effects

(+ 1 2)

(defn wisdom
  [words]
  (str words ", Daniel-san"))

(wisdom "always bathe on sundays")


;; not referentially transparent
(defn year-end-eval
  []
  (if (> (rand) 0.5)
    "You get a raise!"
    "Better luck next year!"))

(defn analyze-file                                          ;; not referentially transparent
  [filename]
  (analysis (slurp filename)))

(defn analysis                                              ;; is ref transparent. Never have to worry about external conditions
  [text]
  (str "chracter count: " (count text)))


(def great-name "Ros")
great-name

(let [great-name "bloodthunder"] great-name)                ;


;; general approach to recusrive problem solving
(defn sum
  ([vals] (sum vals 0))                                     ;; arity 1 -> call sum vals 0
  ([vals accumulating-total]                                ;; arity 2 -> vals and accumulating total
    (if (empty? vals)
      accumulating-total                                    ;;if vals empty return the total
      (sum (rest vals) (+ (first vals) accumulating-total) )))) ;; else sum the rest of vals, add first val to accumulating total


;; using recur for performance
(defn sum
  ([vals]
   (sum vals 0))
  ([vals accumulating-total]
   (if (empty? vals)
     accumulating-total
     (recur (rest vals) (+ (first vals) accumulating-total)))))


(require '[clojure.string :as s])
(defn clean
  [text]
  (s/replace (s/trim text) #"lol" "LOL"))                   ; functional composition - the return value of one function passed to another


;; COMP
;;=====
((comp inc *) 2 3)                                          ; create an anonymous function by composing inc and *

(def character
  {:name "Smooches"
   :attributes {:intelligence 10
                :strength 4
                :dexterity 5}})

(def c-int (comp :intelligence :attributes))
(def c-str (comp :strength :attributes))
(def c-dex (comp :dexterity :attributes))

(c-int character)

(def spell-slots-comp (comp int inc #(/ % 2) c-int))

;; memoisation
(defn sleepy-identity
  [x]
  (Thread/sleep 1000)
  x)
(sleepy-identity "Mr Fantastic")

(def memo-sleepy-ident (memoize sleepy-identity))
(memo-sleepy-ident "mr fan")
(memo-sleepy-ident "mr fan")