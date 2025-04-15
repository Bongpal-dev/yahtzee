package com.bongpal.yatzee.feature.play.model

data class Dice(
    val value: Int = (1..6).random(),
    val isHeld: Boolean = false,
)

internal fun List<Dice>.toggleHold(index: Int): List<Dice> = mapIndexed { i, dice ->
    if (i == index) dice.copy(isHeld = !dice.isHeld) else dice
}

internal fun List<Dice>.rollDices(): List<Dice> = map {
    if (it.isHeld) it else it.copy(value = (1..6).random())
}