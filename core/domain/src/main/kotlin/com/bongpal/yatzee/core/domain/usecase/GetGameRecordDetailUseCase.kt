package com.bongpal.yatzee.core.domain.usecase

import com.bongpal.yatzee.core.domain.repository.GameRecordRepository
import com.bongpal.yatzee.core.model.GameRecord
import javax.inject.Inject

class GetGameRecordDetailUseCase @Inject constructor(
    private val gameRecordRepository: GameRecordRepository
) {
    suspend operator fun invoke(id: String): GameRecord {
        return gameRecordRepository.getRecordDetailById(id)
    }
}