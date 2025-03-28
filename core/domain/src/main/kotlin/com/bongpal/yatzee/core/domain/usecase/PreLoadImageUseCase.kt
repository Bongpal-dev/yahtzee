package com.bongpal.yatzee.core.domain.usecase

import com.bongpal.yatzee.core.domain.repository.ImageCacheRepository
import javax.inject.Inject

class PreLoadImageUseCase @Inject constructor(
    private val imageCacheRepository: ImageCacheRepository
) {
    suspend operator fun invoke() {
        imageCacheRepository.preLoadImage()
    }
}