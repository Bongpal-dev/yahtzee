package com.bongpal.yatzee.core.domain.usecase

import android.graphics.Bitmap
import com.bongpal.yatzee.core.domain.repository.ImageCacheRepository
import com.bongpal.yatzee.core.model.ScoreCategory
import javax.inject.Inject

class GetScoreImageUseCase @Inject constructor(
    private val imageCacheRepository: ImageCacheRepository
) {
    operator fun invoke(): Map<ScoreCategory, Bitmap> {
        return imageCacheRepository.getScoreImages()
    }
}