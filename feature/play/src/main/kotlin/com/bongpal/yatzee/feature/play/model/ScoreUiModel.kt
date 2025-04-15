package com.bongpal.yatzee.feature.play.model

import com.bongpal.yatzee.core.model.Score
import com.bongpal.yatzee.core.model.ScoreCategory
import com.bongpal.yatzee.feature.play.util.calculateScore

data class ScoreUiModel(
    val category: ScoreCategory,
    val point: Int = 0,
    val isSelected: Boolean = false,
    val isPicked: Boolean = false,
) {
    fun pickAndReset(target: ScoreCategory): ScoreUiModel {
        return when {
            category == target -> copy(isPicked = true, isSelected = false)
            isPicked -> this
            else -> copy(point = 0, isSelected = false)
        }
    }
}

fun ScoreUiModel.toModel() = Score(
    category = category,
    point = point
)

internal fun List<ScoreUiModel>.updateScore(dices: List<Dice>) = map { score ->
    if (score.isPicked) score
    else score.copy(
        point = score.category.calculateScore(dices),
        isSelected = false
    )
}