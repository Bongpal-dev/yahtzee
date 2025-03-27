package com.bongpal.yatzee.core.common

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun ImageBitmap.resize(newWidth: Int, newHeight: Int): ImageBitmap =
    withContext(Dispatchers.Default) {
        val prev = android.graphics.Bitmap.createBitmap(this@resize.asAndroidBitmap())
        this@resize.prepareToDraw()
        return@withContext android.graphics.Bitmap.createScaledBitmap(
            prev,
            newWidth,
            newHeight,
            true
        )
            .asImageBitmap()
    }