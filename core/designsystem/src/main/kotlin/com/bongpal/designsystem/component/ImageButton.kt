package com.bongpal.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter

@Composable
fun ImageButton(
    painter: Painter,
    disabledPainter: Painter? = null,
    disabledPressedPainter: Painter? = null,
    contentDescription: String?,
    pressedPainter: Painter? = null,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val defaultPainter = if (enabled) painter else disabledPainter ?: painter
    val pressPainter = if (enabled) pressedPainter ?: painter else disabledPressedPainter ?: painter

    Box(
        modifier = modifier
            .background(color = Color.Transparent)
            .clickable(
                onClick = {
                    if (enabled) onClick()
                },
                indication = null,
                interactionSource = interactionSource,
            ),
    ) {
        Image(
            painter = if (isPressed) pressPainter else defaultPainter,
            contentDescription = contentDescription,
            modifier = Modifier.fillMaxSize(),
        )
    }
}