package com.bongpal.yatzee.feature.play.state

import androidx.compose.ui.geometry.Offset
import com.bongpal.yatzee.core.model.ScoreCategory

data class ScorePopupState(
    val isVisible: Boolean = false,
    val scoreCategory: ScoreCategory? = null,
    val offset: Offset = Offset.Zero,
) {
    val shouldShow: Boolean get() = isVisible && scoreCategory != null
}
