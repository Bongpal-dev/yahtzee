package com.bongpal.yatzee.feature.guide

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
internal fun GuideRoute(
    popBackStack: () -> Unit,
    padding: PaddingValues
) {
    GuideScreen(
        padding = padding
    )
}

@Composable
private fun GuideScreen(
    padding: PaddingValues = PaddingValues()
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        contentAlignment = Alignment.Center
    ) {
        Text("GuideScreen")
    }
}