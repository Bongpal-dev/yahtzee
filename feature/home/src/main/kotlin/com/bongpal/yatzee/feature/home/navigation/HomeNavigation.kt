package com.bongpal.yatzee.feature.home.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.bongpal.yatzee.core.navigation.Route
import com.bongpal.yatzee.feature.home.HomeRoute

fun NavController.navigateHome(navOptions: NavOptions) {
    navigate(Route.Home, navOptions)
}

fun NavGraphBuilder.homeNavGraph(
    navigateToPlay: () -> Unit,
    navigateToScoreBoard: () -> Unit,
    navigateToGuide: () -> Unit,
    paddingValues: PaddingValues
) {
    composable<Route.Home> {
        HomeRoute(
            navigateToPlay = navigateToPlay,
            navigateToScoreBoard = navigateToScoreBoard,
            navigateToGuide = navigateToGuide,
            paddingValues = paddingValues
        )
    }
}