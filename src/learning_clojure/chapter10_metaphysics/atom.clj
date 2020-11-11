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

(defn shuffle-speed
  [zombie]
  (* (:cuddle-hunger zombie)
     (-100 (:percent-deteriorated zombie))))

(defn shuffle-alert
  [key watched old-state new-state]
  (let [sph (shuffle-speed new-state)]
    (if (> sph 500)
      (do
        (println "Run!")
        (println "sph is now " sph)
        (println "This message from " key))
      (do
        (println "Alls well with " key)
        (println "cuddle hunger: " (:cuddle-hunger-level new-state))
        (println "percent deteriorated " (:percent-deteriorated new-state))
        (println "SPH " sph)))))

;; watch functions take 4 args:
;;-- key for reporting, atom being watched, the state before and state after

(reset! fred {:cuddle-hunger-level 22
              :percent-deteriorated 2})
(add-watch fred :fred-shuffle-alert shuffle-alert)          ;; add watch with :fred-shuffle-alert key
(swap! fred update-in [:percent-deteriorated] + 1)          ;; update and trigger watch


;; validators
;;===========
;; allow you to specify what states are allowable for a reference
(defn percent-deteriorated-validator
  [{:keys [percent-deteriorated]}]
  (and (>= percent-deteriorated 0)
       (<= percent-deteriorated 100)))

;; only takes one arg. if validator fails by returning false or throwing an exception

(def bobby
  atom {:cuddle-hunger-level 0 :percent-deteriorated 0}
  :validator percent-deteriorated-validator)

(swap! bobby update-in [:percent-deteriorated] + 200)


;; transaction semantics with REFz
;; provides atomicity, consistency nd isolation

