package com.bongpal.yatzee.feature.main

import android.util.Log
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.bongpal.yatzee.feature.main.component.MainNavHost

fun NavController.getBackStackEntries(): List<NavBackStackEntry> {
    return try {
        val backQueueField = NavController::class.java.getDeclaredField("backQueue")
        backQueueField.isAccessible = true
        @Suppress("UNCHECKED_CAST")
        backQueueField.get(this) as List<NavBackStackEntry>
    } catch (e: Exception) {
        Log.e("NavBackStack", "⚠️ 백스택 추출 실패: ${e.message}")
        emptyList()
    }
}

@Composable
internal fun MainRoute(
    navigator: MainNavigator = rememberMainNavigator()
) {
    val currentEntry by navigator.navController.currentBackStackEntryAsState()

    LaunchedEffect(currentEntry) {
        val entries = navigator.navController.getBackStackEntries()

        Log.d("NavBackStack", "📦 현재 백스택 (${entries.size}개):")
        entries.forEachIndexed { index, entry ->
            val route = entry.destination.route ?: entry.destination.displayName
            Log.d("NavBackStack", "${index + 1}. $route")
        }
        Log.d("NavBackStack", "------------------")
    }

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