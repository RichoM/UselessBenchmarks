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
  
  (defn avg [coll]
    (/ (reduce + coll) (count coll) 1.0))
  (def clj [3712 3697 3717 3670 3679 3672 3699 3644 3609 3672])
  (def clj [3313 3243 3301 3414 3256 3411 3233 3255 3309 3156])
  (avg clj)
  
  (def cpp [3248
            3249
            3252
            3262
            3259
            3267
            3274
            3277
            3306
            3324])
  (avg cpp)
  
  (/ 3677.1 3271.8)
  
  (def cs [3410
           3400
           3416
           3421
           3429
           3409
           3549
           3466
           3495
           3317])
  (avg cs)
  
  (def cpp [3090
            3101
            3086
            3066
            3132
            3137
            3158
            3079
            3184
            3162])
  (/ (avg cs)(avg cpp))
  
  (def cpp [3110
            3058
            3154
            3124
            3050
            3087
            3105
            3044
            3050
            3109])
  (/ (avg clj)
     (avg cpp))
  
  
  (def node [3066
             2986
             2973
             2987
             2995
             2933
             2951
             2966
             2993
             3007])
  (avg node)
  
  (def cpp [3065
            3050
            3059
            3265
            3082
            3097
            3151
            3056
            3052
            3180])
  (avg cpp)
  
  (/ (avg node) (avg cpp))
  )