(ns day03
  (:require [clojure.java.io :as io]
            [clojure.set :as set]))

(defn rucksacks
  [line]
  (let [mid (-> line count (/ 2))]
    (split-at mid line)))

(defn common-item-type [rucksacks]
  (->> rucksacks
       (map set)
       (apply set/intersection)
       (first)))

;; Lowercase item types a through z have priorities 1 through 26.
;; Uppercase item types A through Z have priorities 27 through 52.
;; user> (int \A)
;; 65
;; user> (int \a)
;; 97
(defn letter->priority [c]
  (let [i (int c)]
    (if (>= i 97)
      (- i 96)
      (- i 38))))

(defn part-one []
  (with-open [rdr (-> "day3-input.txt" io/resource io/reader)]
    (->> rdr
         line-seq
         (map #(map letter->priority %))
         (map rucksacks)
         (map common-item-type)
         (apply +))))

(defn part-two []
  (with-open [rdr (-> "day3-input.txt" io/resource io/reader)]
    (->> rdr
         line-seq
         (map #(map letter->priority %))
         (partition 3)
         (map common-item-type)
         (apply +))))

(comment
  (part-one) ;; 8105
  (part-two) ;; 2363
  )
