package com.bongpal.yatzee.feature.scoreboard.scoredetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bongpal.yatzee.core.domain.usecase.GetGameRecordDetailUseCase
import com.bongpal.yatzee.core.domain.usecase.GetScoreImageUseCase
import com.bongpal.yatzee.core.domain.usecase.GetTierImageUseCase
import com.bongpal.yatzee.feature.scoreboard.state.ScoreDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScoreDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getGameRecordDetailUseCase: GetGameRecordDetailUseCase,
    private val getTierImageUseCase: GetTierImageUseCase,
    private val getScoreImageUseCase: GetScoreImageUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(ScoreDetailUiState())
    val uiState = _uiState.asStateFlow()

    init {
        val gameRecordId = savedStateHandle.get<String>("recordId") ?: ""

        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(
                    tierImages = getTierImageUseCase(),
                    gameRecords = getGameRecordDetailUseCase(gameRecordId),
                    scoreImages = getScoreImageUseCase()
                )
            }
        }
    }
}