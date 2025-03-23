package com.bongpal.play.model

data class Score(
    val category: ScoreCategory,
    val point: Int = 0,
    val isSelected: Boolean = false,
    val isPicked: Boolean = false,
)