package com.bongpal.yatzee.core.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object Home : Route

    @Serializable
    data object Play : Route

    @Serializable
    data class Result(val finalScore: Int) : Route

    @Serializable
    data class ScoreBoard(val currentScore: Int?) : Route

    @Serializable
    data object Guide : Route
}