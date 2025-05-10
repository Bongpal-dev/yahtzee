package com.bongpal.yatzee.core.ui.model

import androidx.annotation.DrawableRes

sealed class ImageResource {
    data class Bitmap(val bitmap: android.graphics.Bitmap) : ImageResource()
    data class Drawable(@DrawableRes val drawableId: Int) : ImageResource()
    data class Vector(val vector: androidx.compose.ui.graphics.vector.ImageVector) : ImageResource()
    data object None : ImageResource()
}