package com.bongpal.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.bongpal.home.navigation.navigateHome
import com.bongpal.navigation.Route
import com.bongpal.play.navigation.navigatePlay
import com.bongpal.result.navigation.navigateResult

internal class MainNavigator(
    val navController: NavHostController,
) {
    private val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination
    val startDestination = Route.Home
    private val navOptions by lazy {
        navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateHome() {
        navController.navigateHome(navOptions)
    }

    fun navigatePlay() {
        navController.navigatePlay(navOptions)
    }

    fun navigateResult(finalScore: Int) {
        navController.navigateResult(finalScore, navOptions)
    }
}

@Composable
internal fun rememberMainNavigator(
    navController: NavHostController = rememberNavController(),
): MainNavigator = remember(navController) {
    MainNavigator(navController)
}