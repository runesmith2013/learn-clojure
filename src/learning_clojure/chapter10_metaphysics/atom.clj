;; atoms allow you to endow a succession of related values with a single identity
(def fred (atom {:cuddle-hunger 0
                 :percent-deteriorated 0}))
;; creates new atom and binds it to fred

;; find value by dereferencing
@fred

;; atoms are atomic. can't read and write

;; reference type can be updated to a new value with swap!

(swap! fred
       (fn [current-state])
       (merge-with + current-state {:cuddle-hunger 1}))

@fred                                                       ;; to get the new current state

;; swap both values at the same time, atomically
(swap! fred
       (fn [current-state]
         (merge-with + current-state {:cuddle-hunger-level 1
                                      :percent-deteriorated 1})))

;; update-in
;;----------
(update-in {:a {:b 3}} [:a :b] inc)

(update-in {:a {:b 3}} [:a :b] + 10)

;; by using atoms you can retain past state
(let [num (atom 1)
      s1 @num]
  (swap! num inc)
  (println "state 1:" s1)
  (println "current state " @num))

;; watches and validators
;;=======================


