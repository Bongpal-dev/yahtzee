package com.bongpal.play

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
internal fun PlayRoute(
    paddingValues: PaddingValues,
) {
    PlayScreen(
        paddingValues = paddingValues
    )
}

@Composable
private fun PlayScreen(
    paddingValues: PaddingValues
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "Play Screen",
            style = MaterialTheme.typography.headlineLarge,
        )
    }
}

