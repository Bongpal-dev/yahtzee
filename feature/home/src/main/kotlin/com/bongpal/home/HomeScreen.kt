package com.bongpal.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun HomeRoute(
    navigateToPlay: () -> Unit,
    paddingValues: PaddingValues,
) {
    HomeScreen(
        navigateToPlay = navigateToPlay,
        paddingValues = paddingValues
    )
}

@Composable
private fun HomeScreen(
    navigateToPlay: () -> Unit = {},
    paddingValues: PaddingValues = PaddingValues(),
) {
    Column (
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically),
    ) {
        Text(
            text = "Yatzee",
            style = MaterialTheme.typography.headlineLarge,
        )

        TextButton(
            onClick = navigateToPlay
        ) {
            Text(
                text = "Play",
                style = MaterialTheme.typography.headlineMedium,
            )
        }

    }
}