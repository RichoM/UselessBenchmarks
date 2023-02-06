(ns viz.core
  (:require [oz.core :as oz]
            [oz.server :as server])
  (:gen-class))

(when-not (server/web-server-started?)
  (oz/start-server!))

(def data {:cpp [2958 2871 2917 2929 2968 2910 2917 2912 3290 3028]
           :python-1 [57968 56934 56998 54468 54671 57461 55788 59933 56712 58542]
           :python-2 [69847 69622 71078 72966 76699 72212 75676 76739 74587 73071]
           :pypy-1 [4223 4264 4246 4229 4309 4577 4420 4373 4358 4310]
           :pypy-2 [4298 4083 4282 4127 4103 4119 4117 4139 4213 4222]
           :csharp [3121 3053 3104 3054 3033 3041 3038 3050 3075 3052]
           :js-node [2925 2881 2869 2863 2879 2896 2860 2831 2872 2905]
           :js-browser [3283 2799 2752 2779 2768 2798 2748 2823 2844 2799]
           :squeak [8824 8849 8758 8671 8660 8751 8758 8782 8644 8689]
           :clj-repl [3519 3465 3400 3488 3472 3365 3519 3525 3354 3436]
           :clj-jar [3290 3270 3242 3337 3334 3234 3272 3400 3520 3287]
           :clj-repl-typehints [3392 3307 3186 3334 3353 3453 3508 3363 3417 3447]
           :clj-jar-typehints [3079 3018 3006 3010 3005 2998 3005 3009 3014 3008]
           :cljs-node [4767 4673 4665 4603 4614 4621 4615 4635 4640 4613]
           :cljs-browser [4801 4572 4643 4732 4627 4614 4775 4688 4684 4689]
           :pharo [231665  254002  255554  253962  250770  232864  251823  255950  230748  252192]
           :cuis [56791  56200  56287  56783  61320  68459  59640  56162 82607  62184]
           :cuis-jit [20219  20385  20629  20379  20385  20450  20402  20421  20367  20394]})

(def versions {:cpp "C++ (MSVC)"
               :python-1 "Python (v1)"
               :python-2 "Python (v2)"
               :pypy-1 "Pypy (v1)"
               :pypy-2 "Pypy (v2)"
               :csharp "C# (.Net 6)"
               :js-node "Javascript (Node v14.18.2)"
               :js-browser "Javascript (Chrome 109.0.5414.120)"
               :squeak "Squeak 6.0"
               :clj-repl "Clojure (REPL, no type hints)"
               :clj-jar "Clojure (jar, no type hints)"
               :clj-repl-typehints "Clojure (REPL, w/type hints)"
               :clj-jar-typehints "Clojure (jar, w/type hints)"
               :cljs-node "ClojureScript (Node v14.18.2)"
               :cljs-browser "ClojureScript (Chrome 109.0.5414.120)"
               :pharo "Pharo 10"
               :cuis "Cuis 6.0 (#5510)"
               :cuis-jit "Cuis 6.0 (#5510 vm-jit)"})

(def language-names {:cpp "C++"
                     :python-1 "Python"
                     :python-2 "Python"
                     :pypy-1 "Python"
                     :pypy-2 "Python"
                     :csharp "C#"
                     :js-node "Javascript"
                     :js-browser "Javascript"
                     :squeak "Smalltalk"
                     :clj-repl "Clojure"
                     :clj-jar "Clojure"
                     :clj-repl-typehints "Clojure"
                     :clj-jar-typehints "Clojure"
                     :cljs-node "ClojureScript"
                     :cljs-browser "ClojureScript"
                     :pharo "Pharo"
                     :cuis "Smalltalk"
                     :cuis-jit "Smalltalk"})

(defn generate-vega-doc []
  [:div
   [:vega-lite {:data {:values (let [averages (->> data
                                                   (map (fn [[k v]]
                                                          [k (/ (reduce + v)
                                                                (count v))]))
                                                   (into {}))
                                     baseline (averages :cpp)]
                                 (->> data
                                      (mapcat (fn [[k v]]
                                                (map (fn [t]
                                                       {:language (language-names k)
                                                        :version (versions k)
                                                        :overhead (/ t baseline 1.0)
                                                        :time (/ t 1000.0)})
                                                     v)))))}
                :width 900
                :height 300
                :encoding {:x {:field "version"
                               :type "nominal"
                               :title nil
                               :axis {:labelAngle -30}
                               :sort {:field "time" :op "mean"}}
                           :y {:field "time"
                               :aggregate "mean"
                               :title "Average time (seconds)"
                               :type "quantitative"}
                           :color {:field "language" :title nil}
                           :tooltip [{:field "version" :title "Version"}
                                     {:field "overhead" :title "Average overhead (vs C++)" :aggregate "mean"}
                                     {:field "time" :title "Average  time (seconds)" :aggregate "mean"}]}
                :layer [{:mark {:type "bar"}}]}]

   [:vega-lite {:data {:values (let [averages (->> data
                                                   (map (fn [[k v]]
                                                          [k (/ (reduce + v)
                                                                (count v))]))
                                                   (into {}))
                                     baseline (averages :cpp)]
                                 (->> data
                                      (mapcat (fn [[k v]]
                                                (map (fn [t]
                                                       {:language (language-names k)
                                                        :version (versions k)
                                                        :overhead (/ t baseline 1.0)
                                                        :time (/ t 1000.0)})
                                                     v)))))}
                :width 500
                :height 300
                :title "Grouped by language"
                :encoding {:x {:field "language"
                               :type "nominal"
                               :title nil
                               :axis {:labelAngle -30}
                               :sort {:field "time" :op "mean"}}
                           :y {:field "time"
                               :aggregate "mean"
                               ;:scale {:type "log"}
                               :title "Average time (seconds)"
                               :type "quantitative"}
                           :color {:field "language" :title nil}
                           :tooltip [{:field "language" :title "Language"}
                                     {:field "overhead" :title "Average overhead (vs C++)" :aggregate "mean"}
                                     {:field "time" :title "Average time (seconds)" :aggregate "mean"}]}
                :layer [{:mark {:type "bar"}}]}]

   [:vega-lite {:data {:values (let [averages (->> data
                                                   (map (fn [[k v]]
                                                          [k (/ (reduce + v)
                                                                (count v))]))
                                                   (into {}))
                                     baseline (averages :squeak)]
                                 (->> data
                                      (filter (fn [[k _]] (contains? #{"Smalltalk" "Pharo"}
                                                                     (language-names k))))
                                      (mapcat (fn [[k v]]
                                                (map (fn [t]
                                                       {:language (language-names k)
                                                        :version (versions k)
                                                        :overhead (/ t baseline 1.0)
                                                        :time (/ t 1000.0)})
                                                     v)))))}
                :width 300
                :height 300
                :title "Smalltalk-only"
                :encoding {:x {:field "version"
                               :type "nominal"
                               :title nil
                               :axis {:labelAngle -30}
                               :sort {:field "time" :op "mean"}}
                           :y {:field "time"
                               :aggregate "mean"
                               :title "Average  time (seconds)"
                               :type "quantitative"}
                           :color {:field "version" :title nil}
                           :tooltip [{:field "version" :title "Version"}
                                     {:field "overhead" :title "Average overhead (vs Squeak)" :aggregate "mean"}
                                     {:field "time" :title "Average  time (seconds)" :aggregate "mean"}]}
                :layer [{:mark {:type "bar"}}]}]])

(defn redraw! []
  (println "REDRAW!")
  (oz/view! (generate-vega-doc)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(comment
  (redraw!)
  (oz/export! (generate-vega-doc) "../index.html")
  )