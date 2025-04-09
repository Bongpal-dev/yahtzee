package com.bongpal.yatzee.feature.guide.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bongpal.yatzee.core.navigation.Route
import com.bongpal.yatzee.feature.guide.GuideRoute

fun NavController.navigateGuide() {
    navigate(Route.Guide)
}

fun NavGraphBuilder.guideNavGraph(
    paddingValues: PaddingValues,
    popBackStack: () -> Unit
) {
    composable<Route.Guide> {
        GuideRoute(
            popBackStack = popBackStack,
            padding = paddingValues
        )
    }
}