package com.bongpal.yatzee.feature.play.state

import com.bongpal.yatzee.core.model.ScoreCategory
import com.bongpal.yatzee.core.model.isLower
import com.bongpal.yatzee.core.model.isUpper
import com.bongpal.yatzee.feature.play.model.Dice
import com.bongpal.yatzee.feature.play.model.ScoreUiModel
import com.bongpal.yatzee.feature.play.model.toModel
import com.bongpal.yatzee.feature.play.model.updateScore

data class ScoreState(
    val upper: List<ScoreUiModel> = ScoreCategory.entries
        .filter { it.isUpper() }
        .map { ScoreUiModel(it) },
    val lower: List<ScoreUiModel> = ScoreCategory.entries
        .filter { it.isLower() }
        .map { ScoreUiModel(it) },
    val upperBonus: Boolean = false,
) {
    val upperScore get() = calculateUpperScore()
    val finalScore get() = calculateFinalScore()


    fun clearSelected() = copy(
        upper = upper.map { it.copy(isSelected = false) },
        lower = lower.map { it.copy(isSelected = false) }
    )

    private fun calculateUpperScore() = upper.filter { it.isPicked }.sumOf { it.point }

    private fun calculateFinalScore(): Int {
        val upperPicked = upper.filter { it.isPicked }.sumOf { it.point }
        val lowerPicked = lower.filter { it.isPicked }.sumOf { it.point }
        val bonus = if (upperBonus) 35 else 0

        return upperPicked + lowerPicked + bonus
    }

    fun updateScores(dices: List<Dice>): ScoreState {
        return ScoreState(
            upper = upper.updateScore(dices),
            lower = lower.updateScore(dices)
        )
    }

    fun find(category: ScoreCategory) = (upper + lower).first { it.category == category }

    fun select(category: ScoreCategory): ScoreState {
        return copy(
            upper = upper.map { it.copy(isSelected = it.category == category) },
            lower = lower.map { it.copy(isSelected = it.category == category) }
        )
    }

    fun pick(category: ScoreCategory): ScoreState {
        val updatedUpper = upper.map { it.pickAndReset(category) }
        val updatedLower = lower.map { it.pickAndReset(category) }

        val upperScoreAfterPick = updatedUpper.filter { it.isPicked }.sumOf { it.point }
        val shouldGrantBonus = upperBonus.not() && upperScoreAfterPick >= 63

        return copy(
            upper = updatedUpper,
            lower = updatedLower,
            upperBonus = upperBonus || shouldGrantBonus
        )
    }

    fun isAllPicked() = upper.all { it.isPicked } && lower.all { it.isPicked }

    fun toModelList() = (upper + lower).map { it.toModel() }
}