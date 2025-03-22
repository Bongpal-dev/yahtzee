package com.bongpal.play.model

data class Dice(
    val value: Int = (1..6).random(),
    val isHeld: Boolean = false,
)