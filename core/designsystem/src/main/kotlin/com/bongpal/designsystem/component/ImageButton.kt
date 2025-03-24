package com.bongpal.designsystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.onSizeChanged
import com.bongpal.common.resize

@Composable
fun ImageButton(
    bitmap: ImageBitmap,
    pressedBitmap: ImageBitmap? = null,
    disabledBitmap: ImageBitmap? = null,
    disabledPressedBitmap: ImageBitmap? = null,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val normalBitmap = if (enabled) bitmap else disabledBitmap ?: bitmap
    val pressedStateBitmap =
        if (enabled) pressedBitmap ?: bitmap else disabledPressedBitmap ?: bitmap

    val displayBitmap = if (isPressed) pressedStateBitmap else normalBitmap
    val aspectRatio = displayBitmap.width.toFloat() / displayBitmap.height.toFloat()

    var width by remember { mutableIntStateOf(1) }
    var height by remember { mutableIntStateOf(1) }

    val resizedImage = remember(width, height, displayBitmap) {
        displayBitmap.resize(width, height)
    }

    Box(
        modifier = modifier
            .aspectRatio(aspectRatio)
            .onSizeChanged {
                width = it.width
                height = it.height
            }
            .clickable(
                onClick = {
                    if (enabled) onClick()
                },
                indication = null,
                interactionSource = interactionSource
            )
            .drawBehind {
                drawImage(resizedImage)
            }
    )
}