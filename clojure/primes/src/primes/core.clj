(ns primes.core
  (:gen-class))

(set! *unchecked-math* :warn-on-boxed)
(set! *warn-on-reflection* true)

(defn sqrt [n]
  (Math/sqrt n))

(defn prime? [^long a]
  (cond
    (<= a 1) false
    (zero? (mod a 2)) (= a 2)
    :else
    (let [^double limit (sqrt a)]
      (loop [i 3]
        (if (> i limit)
          true
          (if (zero? (mod a i))
            false
            (recur (+ i 2))))))))

(defn millis []
  (System/currentTimeMillis))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!")
  (let [^long begin-time (millis)
        count (volatile! 0)]
    (loop [j 0]
      (when (< j 10000)
        (vreset! count 0)
        (loop [i 1]
          (when (< i 10000)
            (let [prime (prime? i)]
              (when prime (vswap! count inc))
              (recur (inc i)))))
        (recur (inc j))))
    (let [^long end-time (millis)
          delta-ms (- end-time begin-time)]
      (println delta-ms "ms")
      (println @count "primes found")
      delta-ms)))

(comment
  
  (repeatedly 10 -main) 
  
  )