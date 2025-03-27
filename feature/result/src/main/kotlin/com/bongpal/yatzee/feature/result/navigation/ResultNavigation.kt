package com.bongpal.yatzee.feature.result.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.bongpal.yatzee.core.navigation.Route
import com.bongpal.yatzee.feature.result.ResultRoute

fun NavController.navigateResult(finalScore: Int, navOptions: NavOptions) {
    navigate(Route.Result(finalScore), navOptions)
}

fun NavGraphBuilder.resultNavGraph(
    navigateToHome: () -> Unit,
    navigateToScoreBoard: (Int) -> Unit,
    paddingValues: PaddingValues
) {
    composable<Route.Result> {
        ResultRoute(
            navigateToHome = navigateToHome,
            navigateToScoreBoard = navigateToScoreBoard,
            paddingValues = paddingValues
        )
    }
}