package com.bongpal.yatzee.core.domain.usecase

import com.bongpal.yatzee.core.domain.repository.GameRecordRepository
import javax.inject.Inject

class GetGameRecordsUseCase @Inject constructor(
    private val gameRecordRepository: GameRecordRepository
) {
    operator fun invoke() = gameRecordRepository.getRecords()
}