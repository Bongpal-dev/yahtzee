package com.bongpal.yatzee.feature.play.state

import android.graphics.Bitmap
import com.bongpal.yatzee.core.model.ScoreCategory
import com.bongpal.yatzee.feature.play.model.Dice

internal data class PlayUiState(
    val dices: List<Dice> = emptyList(),
    val scoreInitialImages: Map<ScoreCategory, Bitmap> = emptyMap(),
    val isRolling: Boolean = false,
    val rollCount: Int = 0,
    val scoreState: ScoreState = ScoreState(),
    val isEnd: Boolean = false
)