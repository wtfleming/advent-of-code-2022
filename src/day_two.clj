(ns day-two
  (:require [clojure.java.io :as io]))

(def opponent->move {"A" :rock
                     "B" :paper
                     "C" :scissors})

;; For part 1
(def me->move {"X" :rock
               "Y" :paper
               "Z" :scissors})

;; For part 2
(def me->win-lose-draw {"X" :lose
                        "Y" :draw
                        "Z" :win})

(def move-score {:rock 1
                 :paper 2
                 :scissors 3})

(def winning-move {:rock :paper
                   :paper :scissors
                   :scissors :rock})

(def losing-move {:rock :scissors
                  :paper :rock
                  :scissors :paper})

(defn won? [[opponent me]]
  (or
   (and (= :rock me) (= :scissors opponent))
   (and (= :paper me) (= :rock opponent))
   (and (= :scissors me) (= :paper opponent))))

(defn lost? [[opponent me]]
  (or
   (and (= :rock me) (= :paper opponent))
   (and (= :paper me) (= :scissors opponent))
   (and (= :scissors me) (= :rock opponent))))

(defn round-outcome-score [moves]
  (cond
    (won? moves) 6
    (lost? moves) 0
    :else 3))

(defn round-score [[_opponent me :as moves]]
  (+ (move-score me) (round-outcome-score moves)))

(defn win-lose-draw-action->move [action opponent-move]
  (case action
    :lose (losing-move opponent-move)
    :draw opponent-move
    :win (winning-move opponent-move)))

(defn part-one []
  (->> "day2-input.txt"
       (io/resource)
       (slurp)
       (re-seq #"([A-C]) ([X-Z])")
       (map (fn [[_pattern player1-action player2-action]] [(opponent->move player1-action) (me->move player2-action)]))
       (map round-score)
       (apply +)))

(defn part-two []
  (->> "day2-input.txt"
       (io/resource)
       (slurp)
       (re-seq #"([A-C]) ([X-Z])")
       (map (fn [[_pattern opponent-action me-action]] [(opponent->move opponent-action) (me->win-lose-draw me-action)]))
       (map (fn [[opponent-action win-lose-draw-action]] [opponent-action (win-lose-draw-action->move win-lose-draw-action opponent-action)]))
       (map round-score)
       (apply +)))


(comment
  (part-one) ;; 13565
  (part-two) ;; 12424
  )
