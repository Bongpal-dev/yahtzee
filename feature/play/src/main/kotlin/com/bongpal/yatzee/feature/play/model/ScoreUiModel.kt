package com.bongpal.yatzee.feature.play.model

import com.bongpal.yatzee.core.model.Score
import com.bongpal.yatzee.core.model.ScoreCategory
import com.bongpal.yatzee.core.model.UPPER

data class ScoreUiModel(
    val category: ScoreCategory,
    val point: Int = 0,
    val isSelected: Boolean = false,
    val isPicked: Boolean = false,
) {
    fun isUpper() = category.section == UPPER
}

fun ScoreUiModel.toModel() = Score(
    category = category,
    point = point
)