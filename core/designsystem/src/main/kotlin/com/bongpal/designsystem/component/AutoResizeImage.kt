package com.bongpal.designsystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.onSizeChanged
import com.bongpal.common.resize

@Composable
fun AutoResizeImage(
    bitmap: ImageBitmap,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    val aspectRatio = bitmap.width.toFloat() / bitmap.height.toFloat()
    var width by remember { mutableIntStateOf(1) }
    var height by remember { mutableIntStateOf(1) }

    Box(
        modifier = modifier
            .aspectRatio(aspectRatio)
            .clickable { onClick() }
            .onSizeChanged {
                width = it.width
                height = it.height
            }
    ) {
        val resizedImage by produceState<ImageBitmap?>(
            initialValue = null, key1 = width, key2 = height, key3 = bitmap
        ) { value = bitmap.resize(width, height) }

        Box(
            modifier = Modifier
                .drawBehind { drawImage(resizedImage ?: return@drawBehind) }
        )
    }
}