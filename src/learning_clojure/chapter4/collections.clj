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

(conj [0] [1])