package com.bongpal.yatzee.feature.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.bongpal.yatzee.core.navigation.Route
import com.bongpal.yatzee.feature.guide.navigation.navigateGuide
import com.bongpal.yatzee.feature.home.navigation.navigateHome
import com.bongpal.yatzee.feature.play.navigation.navigatePlay
import com.bongpal.yatzee.feature.result.navigation.navigateResult
import com.bongpal.yatzee.feature.scoreboard.navigation.navigateScoreBoard

internal class MainNavigator(
    val navController: NavHostController,
) {
    private val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination
    val startDestination = Route.Home
    private val navOptions by lazy {
        navOptions {
            popUpTo(navController.graph.findStartDestination().id)
            launchSingleTop = true
        }
    }

    fun navigateHome() {
        navController.navigateHome(navOptions)
    }

    fun navigatePlay() {
        navController.navigatePlay()
    }

    fun navigateScoreBoard(currentScore: Int? = null) {
        navController.navigateScoreBoard(currentScore)
    }

    fun navigateResult(finalScore: Int) {
        navController.navigateResult(finalScore, navOptions)
    }

    fun navigateGuide() {
        navController.navigateGuide()
    }
}

@Composable
internal fun rememberMainNavigator(
    navController: NavHostController = rememberNavController(),
): MainNavigator = remember(navController) {
    MainNavigator(navController)
}