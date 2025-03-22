package com.bongpal.play

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bongpal.play.model.Dice
import com.bongpal.play.model.ScoreCategory
import com.bongpal.play.model.UPPER
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PlayUiState(
    val dices: List<Dice> = emptyList(),
    val isRolling: Boolean = false,
    val upperScore: Int = 0,
    val scores: List<Score> = ScoreCategory.entries.map { Score(it) },
    val finalScore: Int = 0,
)

@HiltViewModel
class PlayViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(PlayUiState())
    val uiState = _uiState.asStateFlow()

    fun rollDice() {
        viewModelScope.launch {
            _uiState.update { state -> state.copy(isRolling = true) }
            delay(700)
            _uiState.update { state -> state.copy(isRolling = false) }
        }

        val dices = if (uiState.value.dices.isEmpty()) {
            List(5) { Dice() }
        } else {
            uiState.value.dices.map { dice -> if (dice.isHeld) dice else dice.copy(value = (1..6).random()) }
        }

        updateScore(dices)

        _uiState.update { state ->
            state.copy(
                dices = dices,
                scores = state.scores.map { it.copy(isSelected = false) }
            )
        }
    }

    fun holdDice(index: Int) {
        _uiState.update { state ->
            state.copy(
                dices = state.dices.mapIndexed { i, dice ->
                    if (i == index) dice.copy(isHeld = !dice.isHeld) else dice
                }
            )
        }
    }

    fun selectScore(score: Score) {
        _uiState.update { state ->
            state.copy(
                scores = state.scores.map { s ->
                    if (s.category == score.category) {
                        s.copy(isSelected = true)
                    } else {
                        s.copy(isSelected = false)
                    }
                }
            )
        }
    }

    fun pickScore(score: Score) {
        _uiState.update { state ->
            val upperScore =
                state.upperScore + if (score.category.section == UPPER) score.point else 0

            state.copy(
                dices = emptyList(),
                scores = state.scores.map { s ->
                    if (s.category == score.category && s.isPicked.not()) {
                        s.copy(
                            isPicked = true,
                            isSelected = false
                        )
                    } else {
                        if (s.isPicked) {
                            s.copy(isSelected = false)
                        } else {
                            s.copy(
                                point = 0,
                                isSelected = false
                            )
                        }
                    }
                },
                upperScore = upperScore,
                finalScore = state.finalScore + score.point + if (upperScore >= 63) 35 else 0
            )
        }
    }

    private fun updateScore(dices: List<Dice>) {
        val count = dices.groupingBy { it.value }.eachCount()
        _uiState.update { state ->
            val scores = state.scores.map { score ->
                if (score.isPicked) return@map score

                when (score.category) {
                    ScoreCategory.ACES -> {
                        score.copy(
                            point = count.getOrDefault(1, 0) * 1
                        )
                    }

                    ScoreCategory.TWOS -> {
                        score.copy(
                            point = count.getOrDefault(2, 0) * 2
                        )
                    }

                    ScoreCategory.THREES -> {
                        score.copy(
                            point = count.getOrDefault(3, 0) * 3
                        )
                    }

                    ScoreCategory.FOURS -> {
                        score.copy(
                            point = count.getOrDefault(4, 0) * 4
                        )
                    }

                    ScoreCategory.FIVES -> {
                        score.copy(
                            point = count.getOrDefault(5, 0) * 5
                        )
                    }

                    ScoreCategory.SIXES -> {
                        score.copy(
                            point = count.getOrDefault(6, 0) * 6
                        )
                    }

                    ScoreCategory.THREE_OF_A_KIND -> {
                        score.copy(
                            point = if (count.values.max() > 2) dices.sumOf { it.value } else 0
                        )
                    }

                    ScoreCategory.SMALL_STRAIGHT -> {
                        val values = dices.map { it.value }.toSet()

                        score.copy(
                            point = if (setOf(1, 2, 3, 4).all { it in values } ||
                                setOf(2, 3, 4, 5).all { it in values } ||
                                setOf(3, 4, 5, 6).all { it in values }) 30 else 0
                        )
                    }

                    ScoreCategory.LARGE_STRAIGHT -> {
                        val values = dices.map { it.value }.toSet()

                        score.copy(
                            point = if (setOf(1, 2, 3, 4, 5).all { it in values } ||
                                setOf(2, 3, 4, 5, 6).all { it in values }) 40 else 0
                        )
                    }

                    ScoreCategory.FOUR_OF_A_KIND -> {
                        score.copy(
                            point = if (count.values.max() > 3) dices.sumOf { it.value } else 0
                        )
                    }

                    ScoreCategory.FULL_HOUSE -> {
                        score.copy(
                            point = if (count.values.contains(3) && count.values.contains(2)) 25 else 0
                        )
                    }

                    ScoreCategory.CHANCE -> {
                        score.copy(
                            point = dices.sumOf { it.value }
                        )
                    }

                    ScoreCategory.YAHTZEE -> {
                        score.copy(
                            point = if (count.values.contains(5)) 50 else 0
                        )
                    }
                }
            }
            state.copy(scores = scores)
        }
    }
}

data class Score(
    val category: ScoreCategory,
    val point: Int = 0,
    val isSelected: Boolean = false,
    val isPicked: Boolean = false,
)