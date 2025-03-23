package com.bongpal.common

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap

fun ImageBitmap.resize(newWidth: Int, newHeight: Int): ImageBitmap {
    val prev = android.graphics.Bitmap.createBitmap(this.asAndroidBitmap())
    this.prepareToDraw()
    return android.graphics.Bitmap.createScaledBitmap(prev, newWidth, newHeight, true)
        .asImageBitmap()
}