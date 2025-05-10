package com.bongpal.yatzee.feature.scoreboard.state

import android.graphics.Bitmap
import com.bongpal.yatzee.core.model.GameRecord
import com.bongpal.yatzee.core.model.ScoreCategory
import com.bongpal.yatzee.core.model.Tier

data class ScoreDetailUiState(
    val gameRecords: GameRecord? = null,
    val tierImages: Map<Tier, Bitmap> = emptyMap(),
    val scoreImages: Map<ScoreCategory, Bitmap> = emptyMap(),
)
