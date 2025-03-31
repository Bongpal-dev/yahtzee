package com.bongpal.yatzee.core.model

enum class ScoreCategory(val section: Int) {
    ACES(UPPER),
    TWOS(UPPER),
    THREES(UPPER),
    FOURS(UPPER),
    FIVES(UPPER),
    SIXES(UPPER),
    THREE_OF_A_KIND(LOWER),
    FULL_HOUSE(LOWER),
    FOUR_OF_A_KIND(LOWER),
    SMALL_STRAIGHT(LOWER),
    CHANCE(LOWER),
    LARGE_STRAIGHT(LOWER),
    YAHTZEE(LOWER),
}

const val UPPER = 0
const val LOWER = 1