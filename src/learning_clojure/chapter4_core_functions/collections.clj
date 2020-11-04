;; Collection abstraction is about data structure as whole
;; e.g. count, empty? and every?

(empty? [])

(empty? ["no!"])

;; INTO - add all elements from one collection to another
;;=====
(map identity {:sunlight "Glitter!"})                       ;; converts map to list
(into {} (identity {:sunlight "Glitter!"}))                 ;; into converts back to a map

(map identity [:garlic :sesame-oil :fried-eggs])
(into #{} (map identity [:garlic-clove :garlic-clove]))     ;; convert vector to list then to set

(into {:favorite-emotion "gloomy"} [[:sunlight-reaction "Glitter!"]]) ;; use into to add elements to map


;; CONJ
;;=====

(conj [0] [1])                                              ; [0 [1]]
(conj [0] 1)                                                ; [0 1]

(conj [0] 1 2 3 4)
(conj {:time "midnight"} [:place "ye olde"])

(defn my-conj
  [target & additions]
  (into target additions))


;; function functions
;====================

; apply - explodes a seqable data struct so it can be passed to a fun
; -----
(max 0 1 2)                                                 ; 2
(max [0 1 2])                                               ; [0 1 2]
(apply max [0 1 2])                                         ; 2

; defining into with conj and apply
(defn my-into
  [target additions]
  (apply conj target additions))

; partial
; ------
; takes a function and any number of args, returns new function
; when new function called, call old function with original args and new args
(def add10 (partial + 10))
(add10 3)                                                   ; 13
(add10 5)                                                   ; 15

(def add-missing0elements
  (partial conj ["Water" "eart" "air"]))

(add-missing0elements "unobtanium" "adamantium")

; partial useful when you're repeating the same combination of function and args
(defn lousy-logger
  [log-level message]
  (condp = log-level
    :warn (clojure.string/lower-case message)
    :emergency (clojure.string/upper-case message)))

(def warn (partial lousy-logger :warn))

(warn "red light ahead!")                                   ; identical to calling (lousy-logger :warn "Red light ahead!")


;; Complement
;;===========
(defn identify-humans [social-security-numbers]
  (filter #(not (vampire? %)))
  (map vampire-related-details social-security-numbers))

(def not-vampire? (complement vampire?))
(defn identify-humans [social-security-numbers]
  (filter not-vampire? (map vampire-related-details social-security-numbers)))
