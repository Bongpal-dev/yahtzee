package com.bongpal.yatzee.feature.play.model

import com.bongpal.yatzee.core.model.ScoreCategory

internal sealed class PlayIntent {
    data object RollDice : PlayIntent()
    data class HoldDice(val index: Int) : PlayIntent()
    data class ClickScore(val category: ScoreCategory) : PlayIntent()
    data object GiveUp : PlayIntent()
}