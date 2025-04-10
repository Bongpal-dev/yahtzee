package com.bongpal.yatzee.core.designsystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bongpal.yatzee.core.designsystem.theme.ActivePink
import com.bongpal.yatzee.core.designsystem.theme.DefaultBlack
import com.bongpal.yatzee.core.designsystem.theme.Typography

@Composable
fun DefaultTextButton(
    modifier: Modifier = Modifier,
    text: String = "",
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    Box(
        modifier = modifier
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .clickable(
                indication = null,
                interactionSource = interactionSource,
                onClick = onClick
            )
    ) {
        Text(
            text = text,
            color = if (isPressed) ActivePink else DefaultBlack,
            style = Typography.titleLarge
        )
    }
}