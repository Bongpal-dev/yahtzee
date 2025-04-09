package com.bongpal.yatzee.feature.play

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bongpal.yatzee.core.domain.usecase.GetScoreImageUseCase
import com.bongpal.yatzee.core.domain.usecase.SaveGameRecordUseCase
import com.bongpal.yatzee.core.model.GameRecord
import com.bongpal.yatzee.core.model.ScoreCategory
import com.bongpal.yatzee.core.model.toTier
import com.bongpal.yatzee.feature.play.model.Dice
import com.bongpal.yatzee.feature.play.model.PlayIntent
import com.bongpal.yatzee.feature.play.model.ScoreUiModel
import com.bongpal.yatzee.feature.play.model.toModel
import com.bongpal.yatzee.feature.play.util.calculateScore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PlayUiState(
    val dices: List<Dice> = emptyList(),
    val scoreInitialImages: Map<ScoreCategory, Bitmap> = emptyMap(),
    val isRolling: Boolean = false,
    val rollCount: Int = 0,
    val upperScore: Int = 0,
    val upperBonus: Boolean = false,
    val scoreUiModels: List<ScoreUiModel> = ScoreCategory.entries.map { ScoreUiModel(it) },
    val finalScore: Int = 0,
    val isEnd: Boolean = false
)

@HiltViewModel
class PlayViewModel @Inject constructor(
    getScoreImageUseCase: GetScoreImageUseCase,
    private val saveGameRecordUseCase: SaveGameRecordUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(PlayUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update { state -> state.copy(scoreInitialImages = getScoreImageUseCase()) }
        }
    }

    fun onAction(intent: PlayIntent) {
        viewModelScope.launch {
            when (intent) {
                is PlayIntent.RollDice -> {
                    if (uiState.value.isRolling) return@launch

                    _uiState.update { state ->
                        state.copy(
                            scoreUiModels = state.scoreUiModels.map { it.copy(isSelected = false) },
                            isRolling = true,
                            dices = state.dices.ifEmpty { List(5) { Dice() } }
                        )
                    }
                    delay(700)
                    _uiState.update { current -> reduce(current, intent) }
                }

                is PlayIntent.ClickScore -> _uiState.update { current -> reduce(current, intent) }

                is PlayIntent.HoldDice -> _uiState.update { current -> reduce(current, intent) }
            }
        }
    }

    private fun reduce(state: PlayUiState, intent: PlayIntent): PlayUiState {
        return when (intent) {
            is PlayIntent.HoldDice -> state.copy(
                dices = state.dices.mapIndexed { i, dice ->
                    if (i == intent.index) dice.copy(isHeld = !dice.isHeld) else dice
                }
            )

            is PlayIntent.RollDice -> {
                val dices =
                    state.dices.map { dice -> if (dice.isHeld) dice else dice.copy(value = (1..6).random()) }

                return state.copy(
                    dices = dices,
                    rollCount = state.rollCount + 1,
                    isRolling = false,
                    scoreUiModels = updateScore(dices, state.scoreUiModels)

                )
            }

            is PlayIntent.ClickScore -> handleScoreClick(state, intent.score)
        }
    }

    private fun handleScoreClick(state: PlayUiState, clicked: ScoreUiModel): PlayUiState {
        if (state.dices.isEmpty() || clicked.isPicked) return state

        val current = state.scoreUiModels.first { it.category == clicked.category }
        val isPickAction = current.isSelected && current.isPicked.not()

        return if (isPickAction) {
            val upperScore = state.upperScore + if (clicked.isUpper()) clicked.point else 0
            val bonusGranted = upperScore >= 63 && state.upperBonus.not()
            val bonus = if (bonusGranted) 35 else 0
            val newScores = state.scoreUiModels.pickScore(clicked)
            val isEnd = newScores.all { it.isPicked }
            val finalScore = state.finalScore + clicked.point + bonus

            state.copy(
                dices = emptyList(),
                scoreUiModels = newScores,
                rollCount = 0,
                upperScore = upperScore,
                upperBonus = state.upperBonus || bonusGranted,
                finalScore = finalScore,
                isEnd = isEnd
            ).also { uiState ->
                if (isEnd) saveGameRecord(uiState)
            }
        } else {
            state.copy(scoreUiModels = state.scoreUiModels.selectScore(clicked))
        }
    }

    private fun List<ScoreUiModel>.selectScore(scoreUiModel: ScoreUiModel): List<ScoreUiModel> {
        return this.map { s ->
            if (s.category == scoreUiModel.category) {
                s.copy(isSelected = true)
            } else {
                s.copy(isSelected = false)
            }
        }
    }

    private fun List<ScoreUiModel>.pickScore(scoreUiModel: ScoreUiModel): List<ScoreUiModel> {
        return this.map { s ->
            when {
                s.category == scoreUiModel.category && s.isPicked.not() -> {
                    s.copy(
                        isPicked = true,
                        isSelected = false
                    )
                }

                s.isPicked -> {
                    s.copy(isSelected = false)
                }

                else -> {
                    s.copy(point = 0, isSelected = false)
                }
            }
        }
    }

    private fun saveGameRecord(playUiState: PlayUiState) {
        viewModelScope.launch {
            saveGameRecordUseCase(
                GameRecord(
                    totalScore = playUiState.finalScore,
                    scores = playUiState.scoreUiModels.map { it.toModel() },
                    tier = playUiState.finalScore.toTier(),
                )
            )
        }
    }

    private fun updateScore(
        dices: List<Dice>,
        currentScoreUiModel: List<ScoreUiModel>
    ): List<ScoreUiModel> {
        return currentScoreUiModel.map { score ->
            if (score.isPicked) score.copy(isSelected = false) else score.copy(
                point = score.category.calculateScore(
                    dices
                ), isSelected = false
            )
        }
    }
}