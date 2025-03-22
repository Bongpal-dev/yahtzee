package com.bongpal.play.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.bongpal.navigation.Route
import com.bongpal.play.PlayRoute

fun NavController.navigatePlay(navOptions: NavOptions) {
    navigate(Route.Play, navOptions)
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