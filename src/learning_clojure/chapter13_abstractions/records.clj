;; Records
;;=======

;; records are custom, maplike data types.
; can implement protocols

(ns were-records)
(defrecord Werewolf [name title])

(Werewolf. "David" "London Tourist")                        ;; class instantiation interop
(->Werewolf "Jacob" " Son of Jacob")
(map->Werewolf {:name "Lucian" :title "CEO of drama"})

;; when you create a record the factory functions ->Recordname and map->recordname are created automatically


;; if you want to use a record in a different namespace you'll need to import it
(ns monster-mash
  (:import [were_records WereWolf]))
(WereWolf. "David" "London Tourist")

;; looking up record values
(def jacob (->WereWolf "Jacob" "Lead Shirt Discarder")
  (.name jacob)                                             ; ; using java interop
  (:name jacob)                                             ; ; map access
  (get jacob :name)


;; any function you can use on a map you can also use on a record
(assoc jacob :title "Third wheel")

  ;                                                         ;
  (defprocotol WereCreature
               (full-moon-behaviour [x]))
  (defrecord WereWolf [name title]
    WereCreature
    (full-moon-behaviour [x]
      (str name " will be naughty"))

    (full-moon-behaviour (map->Werewolf {:name "Lucian" :title "CEO"}))