;; Futures
;;========
;; future creates a new thread and places each expression on it
;; future returns a result

(future (Thread/sleep 4000)
        (println "I'll print after 4 seconds"))
(println "I'll print now")

;; request future value with deref or @ reader macro
(let [result (future (println "This prints once")
                     (+1 1))])
(println "deref: " (deref result))
(println "@: " @result)

;; deref will block if future hasn;t returned.

(let [result (future (Thread/sleep 3000)
                     (+ 1 1))])
(println "Block until retrueved " @result)

(deref (future (Thread/sleep 1000) 0) 10 5)                 ;; return 5 if value not returned in 10ms

(realized? (future (Thread/sleep 1000)))                    ; check whether its realized


;; DELAYS
;;------
;; Define a task without having to exec it immediately

(def jackson-5-delay
  (delay (let [message "just call my name"]
           (println "First deref: " message)
           message)))

;; call using deref or force
(force jackson-5-delay)

;; Promises
;;=========

(def my-promise (promise))
(deliver my-promise (+ 1 2))
@my-promise


(def yak-butter-international
  {:store "Yak Butter International"
   :price 90
   :smoothness 90})
(def butter-than-nothing
  {:store "Butter Than Nothing"
   :price 150
   :smoothness 83})
;; This is the butter that meets our requirements
(def baby-got-yak
  {:store "Baby Got Yak"
   :price 94
   :smoothness 99})

(defn mock-api-call
  [result]
  (Thread/sleep 1000)
  result)

(defn satisfactory?
  "If the butter meets our criteria, return the butter, else return false"
  [butter]
  (and (<= (:price butter) 100)
       (>= (:smoothness butter) 97)
       butter))

(time (some (comp satisfactory? mock-api-call)
            [yak-butter-international butter-than-nothing baby-got-yak]))

;; use promises to define callbacks as in javascript
(let [ferengi-wisdom-promise (promise)]
  (future (println "Here's som wisdom " @ferengi-wisdom-promise)) ;; blocks until value is delivered
  (Thread/sleep 100)
  (deliver ferengi-wisdom-promise "Whisper your way to success"))


;; writing a queue using macros and concurrency
;; take whatever form you give and insert a call to thread/sleep before exec
(defmacro wait
  "Sleep `timeout` seconds before evaluating body"
  [timeout & body]
  `(do (Thread/sleep ~timeout) ~@body))

(let [saying3 (promise)]
  (future (deliver saying3 (wait 100 "Cheerio!")))
  @(let [saying2 (promise)]
     (future (deliver saying2 (wait 400 "Pip pip!")))
     @(let [saying1 (promise)]
        (future (deliver saying1 (wait 200 "'ello govna")))
        (println @saying1)
        saying1)
     (println @saying2)
     saying2)
  (println @saying3)
  saying3)
;; create a promise for each task and a future that will deliver a concurrently computed value to the promise
;; ensures that all futures are created before promises are derefed
; derefing the let block allows us to abstract this with a macro!