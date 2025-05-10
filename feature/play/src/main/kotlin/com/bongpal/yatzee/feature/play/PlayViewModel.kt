package com.bongpal.yatzee.feature.play

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bongpal.yatzee.core.domain.usecase.GetScoreImageUseCase
import com.bongpal.yatzee.core.domain.usecase.SaveGameRecordUseCase
import com.bongpal.yatzee.core.model.GameRecord
import com.bongpal.yatzee.core.model.ScoreCategory
import com.bongpal.yatzee.core.model.toTier
import com.bongpal.yatzee.feature.play.model.Dice
import com.bongpal.yatzee.feature.play.model.PlayIntent
import com.bongpal.yatzee.feature.play.model.rollDices
import com.bongpal.yatzee.feature.play.model.toggleHold
import com.bongpal.yatzee.feature.play.state.PlayUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
internal class PlayViewModel @Inject constructor(
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
                            scoreState = state.scoreState.clearSelected(),
                            isRolling = true,
                            dices = state.dices.ifEmpty { List(5) { Dice() } }
                        )
                    }
                    delay(700)
                    _uiState.update { current -> reduce(current, intent) }
                }

                is PlayIntent.ClickScore -> {
                    if (uiState.value.dices.isEmpty() || uiState.value.isRolling) return@launch

                    _uiState.update { current -> reduce(current, intent) }
                }

                is PlayIntent.HoldDice -> _uiState.update { current -> reduce(current, intent) }

                is PlayIntent.GiveUp -> _uiState.update { current -> reduce(current, intent) }
            }

            if (uiState.value.isEnd) {
                saveGameRecord(uiState.value)
            }
        }
    }

    private fun reduce(state: PlayUiState, intent: PlayIntent): PlayUiState {
        return when (intent) {
            is PlayIntent.HoldDice -> state.copy(
                dices = state.dices.toggleHold(intent.index)
            )

            is PlayIntent.RollDice -> state.dices.rollDices().let { new ->
                state.copy(
                    dices = new,
                    rollCount = state.rollCount + 1,
                    isRolling = false,
                    scoreState = state.scoreState.updateScores(new)
                )
            }

            is PlayIntent.ClickScore -> handleScoreClick(state, intent.category)

            is PlayIntent.GiveUp -> {
                state.copy(isEnd = true)
            }
        }
    }

    private fun handleScoreClick(state: PlayUiState, category: ScoreCategory): PlayUiState {
        val current = state.scoreState.find(category)

        if (state.dices.isEmpty() || current.isPicked) return state

        return if (current.isSelected) {
            val newScores = state.scoreState.pick(category)
            val isEnd = newScores.isAllPicked()

            state.copy(
                dices = emptyList(),
                scoreState = newScores,
                rollCount = 0,
                isEnd = isEnd
            )
        } else {
            state.copy(scoreState = state.scoreState.select(category))
        }
    }

    private fun saveGameRecord(playUiState: PlayUiState) {
        viewModelScope.launch {
            saveGameRecordUseCase(
                GameRecord(
                    id = UUID.randomUUID().toString(),
                    totalScore = playUiState.scoreState.finalScore,
                    scores = playUiState.scoreState.toModelList(),
                    tier = playUiState.scoreState.finalScore.toTier(),
                )
            )
        }
    }
}