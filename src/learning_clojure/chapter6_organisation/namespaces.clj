(ns-name *ns*)                                              ;;refer to current namespace with *ns*. get its name with (ns-name)

inc
'inc
(map inc [1 2])

(def great-books ["East of Eden" "Another book"])           ; interning a var. associate a var with the default namespace

(ns-interns *ns*)                                           ; get a map of interned vars

(get (ns-interns *ns*) 'great-books)                         ; get a specific var from the map

(ns-map *ns*)                                               ; full map used to lookup a var
(deref #'learning-clojure.core/great-books)

(def great-books ["The Power of Bees" "Journey to Upstairs"]) ; namespace collision. Overwrites var value

(create-ns 'cheese.taxonomy)                                ; create and return namespace

(ns-name (create-ns 'cheese))

(in-ns 'cheese.analysis)                                    ; create and switch to namespace

cheese.taxonomy/cheddars                                    ; use fqn for vars in different namespace

;; REFER
;;------

; fine grain control over refering to objects in other namespaces
(clojure.core/refer 'cheese.taxonomy)
; merges vars with ns-map of current namespace
;: only, :exclude, :rename
(clojure.core/refer 'cheese.taxonomy :only ['bries])
(clojure.core/refer 'cheese.taxonomy :exclude ['bries])
(clojure.core/refer 'cheese.taxonomy :rename {'bries 'yummy-bries})


;; defn-
;;-----
;; allows functions to be available only within namespace ie private
(clojure.core/refer 'clojure.core)

(in-ns 'cheese.analysis)
(defn- private-function
  "Does nothing"
  [])

;; ALIAS
;;------
(clojure.core/alias 'taxonomy 'cheese.taxonomy)


(ns the-divine-cheese-code.core                             ;; don't need to quote symbol
  (:require the-divine-cheese-code.visualization.svg))
; is equivalent to

(in-ns 'the-divine-cheese-code.core)
(require 'the-divine-cheese-code.core.visualization.svg)

(ns the-divine-cheese-code.core
  (:require [the-divine-cheese-code.visualization.svg :as svg])) ; can alias

(ns the-divine-cheese-code.core
  (:require [the-divine-cheese-code.visualization.svg :as svg]
            [clojure.java.browse :as browse]))              ;; require multiple libaries

(ns the-divine-cheese-code.core
  (:require [the-divine-cheese-code.visualization.svg :refer [points]])) ;; can refer functions





