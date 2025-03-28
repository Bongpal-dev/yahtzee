package com.bongpal.yatzee.core.domain.usecase

import android.graphics.Bitmap
import com.bongpal.yatzee.core.domain.repository.ImageCacheRepository
import com.bongpal.yatzee.core.model.ImageSize
import com.bongpal.yatzee.core.model.Tier
import javax.inject.Inject

class GetTierImageUseCase @Inject constructor(
    private val imageCacheRepository: ImageCacheRepository
) {
    operator fun invoke(tier: Tier, size: ImageSize): Bitmap? {
        return imageCacheRepository.getTierImage(tier, size)
    }

    operator fun invoke(): Map<Tier, Bitmap> {
        return imageCacheRepository.getTierImages()
    }
}