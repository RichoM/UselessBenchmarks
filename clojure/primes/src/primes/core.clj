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
  
  (def data
    {:cpp [2958
           2871
           2917
           2929
           2968
           2910
           2917
           2912
           3290
           3028]
     :python-1 [57968
                56934
                56998
                54468
                54671
                57461
                55788
                59933
                56712
                58542]
     :python-2 [69847
                69622
                71078
                72966
                76699
                72212
                75676
                76739
                74587
                73071]
     :pypy-1 [4223
              4264
              4246
              4229
              4309
              4577
              4420
              4373
              4358
              4310]
     :pypy-2 [4298
              4083
              4282
              4127
              4103
              4119
              4117
              4139
              4213
              4222]
     :csharp [3121
              3053
              3104
              3054
              3033
              3041
              3038
              3050
              3075
              3052]
     :js-node [2925
               2881
               2869
               2863
               2879
               2896
               2860
               2831
               2872
               2905]
     :js-browser [3283
                  2799
                  2752
                  2779
                  2768
                  2798
                  2748
                  2823
                  2844
                  2799]
     :squeak [8824
              8849
              8758
              8671
              8660
              8751
              8758
              8782
              8644
              8689]
     :clj-repl [3519
                3465
                3400
                3488
                3472
                3365
                3519
                3525
                3354
                3436]
     :clj-jar [3290
               3270
               3242
               3337
               3334
               3234
               3272
               3400
               3520
               3287]
     :clj-repl-typehints [3392
                          3307
                          3186
                          3334
                          3353
                          3453
                          3508
                          3363
                          3417
                          3447]
     :clj-jar-typehints [3079
                         3018
                         3006
                         3010
                         3005
                         2998
                         3005
                         3009
                         3014
                         3008]
     :cljs-node [4767
                 4673
                 4665
                 4603
                 4614
                 4621
                 4615
                 4635
                 4640
                 4613]
     :cljs-browser [4801
                    4572
                    4643
                    4732
                    4627
                    4614
                    4775
                    4688
                    4684
                    4689]
     :pharo [231665 
             254002 
             255554 
             253962 
             250770 
             232864 
             251823 
             255950 
             230748 
             252192]
     :cuis [56791 
            56200 
            56287 
            56783 
            61320 
            68459 
            59640 
            56162
            82607 
            62184]
     :cuis-jit [20219 
                20385 
                20629 
                20379 
                20385 
                20450 
                20402 
                20421 
                20367 
                20394]})
  )