package com.bongpal.result.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.bongpal.navigation.Route
import com.bongpal.result.ResultRoute

fun NavController.navigateResult(finalScore: Int, navOptions: NavOptions) {
    navigate(Route.Result(finalScore), navOptions)
}

fun NavGraphBuilder.resultNavGraph(
    navigateToHome: () -> Unit,
    paddingValues: PaddingValues
) {
    composable<Route.Result> {
        ResultRoute(
            navigateToHome = navigateToHome,
            paddingValues = paddingValues
        )
    }
}