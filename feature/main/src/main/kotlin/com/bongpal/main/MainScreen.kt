package com.bongpal.main

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bongpal.main.component.MainNavHost

@Composable
internal fun MainRoute(
    navigator: MainNavigator = rememberMainNavigator()
) {
    MainScreen(
        navigator = navigator
    )
}

@Composable
private fun MainScreen(
    modifier: Modifier = Modifier,
    navigator: MainNavigator,
) {
    Scaffold(
        modifier = modifier,
    ) { padding ->
        MainNavHost(
            navigator = navigator,
            paddingValues = padding
        )
    }
}