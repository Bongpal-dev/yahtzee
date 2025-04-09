package com.bongpal.yatzee.feature.play.model

sealed class PlayIntent {
    data object RollDice : PlayIntent()
    data class HoldDice(val index: Int) : PlayIntent()
    data class ClickScore(val score: ScoreUiModel) : PlayIntent()
}