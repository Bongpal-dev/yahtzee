package com.bongpal.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
internal fun HomeRoute(
    paddingValues: PaddingValues,
) {
    HomeScreen(
        paddingValues = paddingValues
    )
}

@Composable
private fun HomeScreen(
    paddingValues: PaddingValues = PaddingValues(),
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "Home",
            style = MaterialTheme.typography.headlineLarge,
        )
    }
}