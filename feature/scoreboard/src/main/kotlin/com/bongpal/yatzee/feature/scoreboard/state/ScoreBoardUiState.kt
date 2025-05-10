package com.bongpal.yatzee.feature.scoreboard.state

import android.graphics.Bitmap
import com.bongpal.yatzee.core.model.Tier

data class ScoreBoardUiState(
    val currentScore: Int? = null,
    val tierImages: Map<Tier, Bitmap> = emptyMap()
)