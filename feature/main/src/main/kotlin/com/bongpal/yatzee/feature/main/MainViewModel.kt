package com.bongpal.yatzee.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bongpal.yatzee.core.domain.usecase.PreLoadImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val preLoadImageUseCase: PreLoadImageUseCase
) : ViewModel() {
    fun preLoadImage() {
        viewModelScope.launch {
            preLoadImageUseCase()
        }
    }
}