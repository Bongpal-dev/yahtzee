package com.bongpal.play

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayViewModel @Inject constructor() : ViewModel() {
    private val _diceState = MutableStateFlow<List<Dice>>(emptyList())
    val diceState = _diceState.asStateFlow()

    private val _rollingState = MutableStateFlow(false)
    val rollingState = _rollingState.asStateFlow()

    fun rollDice() {
        _diceState.update { dices ->
            if (dices.isEmpty()) {
                List(5) { Dice() }
            } else {
                dices.map { dice -> if (dice.isHeld) dice else dice.copy(value = (1..6).random()) }
            }
        }
        viewModelScope.launch {
            _rollingState.update { true }
            delay(800)
            _rollingState.update { false }
        }
    }

    fun holdDice(index: Int) {
        _diceState.update { dices ->
            dices.mapIndexed { i, dice ->
                if (i == index) dice.copy(isHeld = !dice.isHeld) else dice
            }
        }
    }
}

data class Dice(
    val value: Int = (1..6).random(),
    val isHeld: Boolean = false,
)