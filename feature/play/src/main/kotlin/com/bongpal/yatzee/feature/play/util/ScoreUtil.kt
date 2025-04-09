package com.bongpal.yatzee.feature.play.util

import com.bongpal.yatzee.core.model.ScoreCategory
import com.bongpal.yatzee.core.model.ScoreCategory.ACES
import com.bongpal.yatzee.core.model.ScoreCategory.CHANCE
import com.bongpal.yatzee.core.model.ScoreCategory.FIVES
import com.bongpal.yatzee.core.model.ScoreCategory.FOURS
import com.bongpal.yatzee.core.model.ScoreCategory.FOUR_OF_A_KIND
import com.bongpal.yatzee.core.model.ScoreCategory.FULL_HOUSE
import com.bongpal.yatzee.core.model.ScoreCategory.LARGE_STRAIGHT
import com.bongpal.yatzee.core.model.ScoreCategory.SIXES
import com.bongpal.yatzee.core.model.ScoreCategory.SMALL_STRAIGHT
import com.bongpal.yatzee.core.model.ScoreCategory.THREES
import com.bongpal.yatzee.core.model.ScoreCategory.THREE_OF_A_KIND
import com.bongpal.yatzee.core.model.ScoreCategory.TWOS
import com.bongpal.yatzee.core.model.ScoreCategory.YAHTZEE
import com.bongpal.yatzee.feature.play.model.Dice

internal fun ScoreCategory.calculateScore(dices: List<Dice>): Int {
    val count = dices.groupingBy { it.value }.eachCount()
    val values = dices.map { it.value }.toSet()

    return when (this) {
        ACES -> count.getOrDefault(1, 0) * 1
        TWOS -> count.getOrDefault(2, 0) * 2
        THREES -> count.getOrDefault(3, 0) * 3
        FOURS -> count.getOrDefault(4, 0) * 4
        FIVES -> count.getOrDefault(5, 0) * 5
        SIXES -> count.getOrDefault(6, 0) * 6
        THREE_OF_A_KIND -> if (count.values.max() > 2) dices.sumOf { it.value } else 0
        FOUR_OF_A_KIND -> if (count.values.max() > 3) dices.sumOf { it.value } else 0
        FULL_HOUSE -> if (count.values.contains(3) && count.values.contains(2)) 25 else 0
        SMALL_STRAIGHT -> if (
            setOf(1, 2, 3, 4).all { it in values } ||
            setOf(2, 3, 4, 5).all { it in values } ||
            setOf(3, 4, 5, 6).all { it in values }
        ) 30 else 0

        LARGE_STRAIGHT -> if (
            setOf(1, 2, 3, 4, 5).all { it in values } ||
            setOf(2, 3, 4, 5, 6).all { it in values }
        ) 40 else 0

        CHANCE -> dices.sumOf { it.value }
        YAHTZEE -> if (count.values.contains(5)) 50 else 0
    }
}