package com.bongpal.yatzee.feature.scoreboard.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bongpal.yatzee.core.navigation.Route
import com.bongpal.yatzee.feature.scoreboard.ScoreBoardRoute

fun NavController.navigateScoreBoard(currentScore: Int? = null) {
    navigate(Route.ScoreBoard(currentScore))
}

fun NavGraphBuilder.scoreBoardNavGraph(
    navigateToHome: () -> Unit,
    popBackStack: () -> Unit,
    paddingValues: PaddingValues
) {
    composable<Route.ScoreBoard> {
        ScoreBoardRoute(
            navigateToHome = navigateToHome,
            popBackStack = popBackStack,
            paddingValues = paddingValues
        )
    }
}