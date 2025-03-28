package com.bongpal.yatzee.feature.result

import android.graphics.Bitmap
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bongpal.yatzee.core.domain.usecase.GetTierImageUseCase
import com.bongpal.yatzee.core.model.ImageSize
import com.bongpal.yatzee.core.model.toTier
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ResultUiState(
    val finalScore: Int = 0,
    val tierImage: Bitmap? = null
)

@HiltViewModel
class ResultViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getTierImageUseCase: GetTierImageUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(ResultUiState())
    val uiState = _uiState.asStateFlow()


    init {
        val finalScore = savedStateHandle.get<Int>("finalScore") ?: -1

        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(
                    finalScore = finalScore,
                    tierImage = getTierImageUseCase(finalScore.toTier(), ImageSize.SCREEN_65PERCENT)
                )
            }
        }
    }
}