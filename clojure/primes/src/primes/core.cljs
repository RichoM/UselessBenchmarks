(ns primes.core)

(defn sqrt [n]
  (Math/sqrt n))

(defn prime? [a]
  (cond
    (<= a 1) false
    (zero? (mod a 2)) (= a 2)
    :else
    (let [limit (sqrt a)]
      (loop [i 3]
        (if (> i limit)
          true
          (if (zero? (mod a i))
            false
            (recur (+ i 2))))))))

(defn millis [] (js/Date.now))

(defn ^:export main [& args]
  (println "Hello world!")
  (let [begin-time (millis)
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
    (let [end-time (millis)
          delta-ms (- end-time begin-time)]
      (println delta-ms "ms")
      (println @count "primes found")
      delta-ms)))

(comment
  
  (repeatedly 10 main)
  )