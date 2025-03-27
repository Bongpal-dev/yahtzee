package com.bongpal.yatzee.core.domain.repository

import androidx.paging.PagingData
import com.bongpal.yatzee.core.model.GameRecord
import kotlinx.coroutines.flow.Flow

interface GameRecordRepository {
    fun getRecords(): Flow<PagingData<GameRecord>>
    suspend fun saveRecord(gameRecord: GameRecord)
}