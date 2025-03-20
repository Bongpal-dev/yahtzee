package com.bongpal.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object Home : Route

    @Serializable
    data object Play: Route

    @Serializable
    data object Result: Route
}