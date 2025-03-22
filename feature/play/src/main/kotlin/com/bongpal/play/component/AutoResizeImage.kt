package com.bongpal.play.component

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.onSizeChanged
import com.bongpal.play.util.resize

@Composable
internal fun AutoResizeImage(
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
            val resizedImage = remember(width, height, bitmap) { bitmap.resize(width, height) }

            Box(
                modifier = Modifier
                    .drawBehind { drawImage(resizedImage) }
            )
        }
}