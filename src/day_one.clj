(ns day-one
  (:require [clojure.java.io :as io]
            [clojure.string :as s]))

(defn- strings->ints
  "Converts a sequence of strings into a lazy sequence of integers"
  [x]
  (map #(Integer/parseInt %) x))

(defn- amounts []
  (->> "day1-input.txt"
       (io/resource)
       (slurp)
       (s/split-lines)
       (partition-by (partial = ""))
       (filter (partial not= [""]))
       (map strings->ints)
       (map #(apply + %))))

(defn part-one []
  (->> (amounts)
       (apply max)))

(defn part-two []
  (->> (amounts)
       (sort >)
       (take 3)
       (apply +)))


(comment
  (part-one) ;; 72602
  (part-two) ;; 207410
  )
