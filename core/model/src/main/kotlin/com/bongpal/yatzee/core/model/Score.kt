package com.bongpal.yatzee.core.model

data class Score(
    val category: ScoreCategory,
    val point: Int = 0,
)