package com.bongpal.yatzee.feature.play.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bongpal.yatzee.core.navigation.Route
import com.bongpal.yatzee.feature.play.PlayRoute

fun NavController.navigatePlay() {
    navigate(Route.Play)
}

fun NavGraphBuilder.playNavGraph(
    navigateToResult: (Int) -> Unit,
    paddingValues: PaddingValues
) {
    composable<Route.Play> {
        PlayRoute(
            navigateToResult = navigateToResult,
            paddingValues = paddingValues
        )
    }
}