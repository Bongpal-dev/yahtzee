package com.bongpal.yatzee.core.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.imageResource
import com.bongpal.yatzee.core.common.resize
import com.bongpal.yatzee.core.ui.model.TierUiModel
import com.bongpal.yatzee.core.ui.model.toUiModel

@Composable
fun TierIcon(
    score: Int,
    modifier: Modifier = Modifier,
) {
    val tierIconRes = score.toUiModel().toImageBitmap()
    val ratio = tierIconRes.width / tierIconRes.height.toFloat()
    var imgWidth by remember { mutableStateOf(1) }
    var imgHeight by remember { mutableStateOf(1) }

    Box(
        modifier = modifier
            .aspectRatio(ratio)
            .onSizeChanged {
                imgWidth = it.width
                imgHeight = it.height
            }
    ) {
        val resizedImage by produceState<ImageBitmap?>(
            initialValue = null, key1 = imgWidth, key2 = imgHeight, key3 = tierIconRes
        ) { value = tierIconRes.resize(imgWidth, imgHeight) }

        Image(
            bitmap = resizedImage ?: return,
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
internal fun TierUiModel.toImageBitmap(): ImageBitmap {
    return ImageBitmap.imageResource(resId)
}