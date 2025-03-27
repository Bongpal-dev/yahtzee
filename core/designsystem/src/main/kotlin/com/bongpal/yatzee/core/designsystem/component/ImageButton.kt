package com.bongpal.yatzee.core.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun ImageButton(
    imageVector: ImageVector,
    pressedImage: ImageVector? = null,
    disabledImage: ImageVector? = null,
    disabledPressedImage: ImageVector? = null,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val defaultButton = if (enabled) imageVector else disabledImage ?: imageVector
    val pressedButton =
        if (enabled) pressedImage ?: imageVector else disabledPressedImage ?: imageVector

    Image(
        imageVector = if (isPressed) pressedButton else defaultButton,
        contentDescription = null,
        modifier = modifier
            .height(52.dp)
            .clickable(
                onClick = {
                    if (enabled) onClick()
                },
                indication = null,
                interactionSource = interactionSource
            )
    )
}