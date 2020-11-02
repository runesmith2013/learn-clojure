;Use the str, vector, list, hash-map, and hash-set functions.
(str "100" "200" "300")                                     ;"100200300"
(vector 1 2 3 4)                                            ; [1 2 3 4]
(list 1 2 3 4)                                              ; (1 2 3 4)
(hash-map 1 2 3 4)                                          ; {1 2, 3 4}
(hash-set 1 2 3 4 5)                                        ; #{1 4 3 2 5}
(set [1 2 3 4])                                             ; #{1 4 3 2}

; Write a function that takes a number and adds 100 to it.
(defn my-inc
  [x]
  (+ x 100))

; Write a function, dec-maker, that works exactly like the function inc-maker except with subtraction:
(defn dec-maker
  "Create a custom decrementor"
  [dec-by]
  #(- % dec-by))

(def dec9 (dec-maker 9))
