package com.bongpal.yatzee.feature.play.model

import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.bongpal.yatzee.core.designsystem.theme.ActivePink
import com.bongpal.yatzee.core.designsystem.theme.LightGray
import com.bongpal.yatzee.core.model.Score
import com.bongpal.yatzee.core.model.ScoreCategory
import com.bongpal.yatzee.core.ui.model.ImageResource
import com.bongpal.yatzee.feature.play.util.calculateScore
import com.bongpal.yatzee.feature.play.util.getScoreImage

internal data class ScoreUiModel(
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

internal fun ScoreUiModel.toModel() = Score(
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

internal fun ScoreUiModel.getScorePoint(isRolling: Boolean): Int? {
    if (isRolling && isPicked.not()) return null
    if (isPicked.not() && isSelected.not() && point < 1) return null
    return point
}

internal fun ScoreUiModel.getScoreColor(): Color = when {
    isPicked -> Color.Black
    isSelected -> ActivePink
    else -> LightGray
}

@Composable
internal fun ScoreUiModel.getDisplayImage(scoreImages: Map<ScoreCategory, Bitmap>): ImageResource =
    if (isPicked.not() && isSelected.not())
        ImageResource.Bitmap(scoreImages.getValue(category))
    else
        ImageResource.Vector(getScoreImage())
