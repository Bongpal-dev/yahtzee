package com.bongpal.yatzee.feature.main.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import com.bongpal.yatzee.feature.guide.navigation.guideNavGraph
import com.bongpal.yatzee.feature.home.navigation.homeNavGraph
import com.bongpal.yatzee.feature.main.MainNavigator
import com.bongpal.yatzee.feature.play.navigation.playNavGraph
import com.bongpal.yatzee.feature.result.navigation.resultNavGraph
import com.bongpal.yatzee.feature.scoreboard.navigation.scoreBoardNavGraph

@Composable
internal fun MainNavHost(
    modifier: Modifier = Modifier,
    navigator: MainNavigator,
    paddingValues: PaddingValues
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        NavHost(
            navController = navigator.navController,
            startDestination = navigator.startDestination
        ) {
            homeNavGraph(
                navigateToPlay = navigator::navigatePlay,
                navigateToScoreBoard = navigator::navigateScoreBoard,
                navigateToGuide = navigator::navigateGuide,
                paddingValues = paddingValues
            )

            playNavGraph(
                navigateToResult = navigator::navigateResult,
                paddingValues = paddingValues
            )

            resultNavGraph(
                navigateToHome = navigator::navigateHome,
                navigateToScoreBoard = navigator::navigateScoreBoard,
                paddingValues = paddingValues
            )

            scoreBoardNavGraph(
                navigateToHome = navigator::navigateHome,
                popBackStack = navigator.navController::popBackStack,
                paddingValues = paddingValues
            )

            guideNavGraph(
                paddingValues = paddingValues,
                popBackStack = navigator.navController::popBackStack
            )
        }
    }
}