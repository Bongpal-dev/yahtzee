package com.bongpal.yatzee.core.model

data class GameRecord(
    val id: String? = null,
    val totalScore: Int,
    val scores: List<Score>,
    val tier: Tier,
    val player: String = ""
)