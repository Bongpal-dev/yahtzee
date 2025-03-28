package com.bongpal.yatzee.core.domain.repository

import android.graphics.Bitmap
import com.bongpal.yatzee.core.model.ImageSize
import com.bongpal.yatzee.core.model.ScoreCategory
import com.bongpal.yatzee.core.model.Tier

interface ImageCacheRepository {
    suspend fun preLoadImage()
    fun getTierImage(tier: Tier, size: ImageSize): Bitmap
    fun getTierImages(): Map<Tier, Bitmap>
    fun getScoreImages(): Map<ScoreCategory, Bitmap>
}