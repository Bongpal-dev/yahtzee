package com.bongpal.play.model

import android.icu.text.CaseMap.Upper

enum class ScoreCategory(val displayName: String, val section: Int) {
    ACES("Aces", UPPER),
    TWOS("Twos", UPPER),
    THREES("Threes", UPPER),
    FOURS("Fours", UPPER),
    FIVES("Fives", UPPER),
    SIXES("Sixes", UPPER),
    THREE_OF_A_KIND("Three-Of-A-Kind", LOWER),
    FOUR_OF_A_KIND("Four-Of-A-Kind", LOWER),
    FULL_HOUSE("Full House", LOWER),
    SMALL_STRAIGHT("Small Straight", LOWER),
    LARGE_STRAIGHT("Large Straight", LOWER),
    CHANCE("Chance", LOWER),
    YAHTZEE("Yahtzee", LOWER),
}

const val UPPER = 0
const val LOWER = 1