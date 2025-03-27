package com.bongpal.yatzee.feature.result

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class ResultUiState(
    val finalScore: Int = 0,
)

@HiltViewModel
class ResultViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ResultUiState())
    val uiState = _uiState.asStateFlow()


    init {
        val finalScore = savedStateHandle.get<Int>("finalScore") ?: -1
        
        _uiState.update { it.copy(finalScore = finalScore) }
    }
}