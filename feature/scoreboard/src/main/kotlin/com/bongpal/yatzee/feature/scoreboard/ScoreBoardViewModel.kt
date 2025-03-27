package com.bongpal.yatzee.feature.scoreboard

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bongpal.yatzee.core.domain.usecase.GetGameRecordsUseCase
import com.bongpal.yatzee.core.model.GameRecord
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ScoreBoardUiState(
    val currentScore: Int? = null
)

@HiltViewModel
class ScoreBoardViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getGameRecordsUseCase: GetGameRecordsUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ScoreBoardUiState())
    val uiState = _uiState.asStateFlow()

    private val _gameRecords = MutableStateFlow<PagingData<GameRecord>>(PagingData.empty())
    val gameRecords = _gameRecords.asStateFlow().cachedIn(viewModelScope)

    init {
        val currentScore = savedStateHandle.get<Int>("currentScore")

        currentScore?.let {
            _uiState.update { it.copy(currentScore = currentScore) }
        }

        viewModelScope.launch {
            getGameRecordsUseCase().collectLatest(_gameRecords::emit)
        }
    }
}