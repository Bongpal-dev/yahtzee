package com.bongpal.yatzee.core.local.api

import androidx.paging.PagingSource
import com.bongpal.yatzee.core.local.entity.GameRecordEntity
import com.bongpal.yatzee.core.model.GameRecord

interface LocalGameRecordDataSource {
    suspend fun insertRecord(gameRecord: GameRecord)
    fun getRecords(): PagingSource<Int, GameRecordEntity>
    suspend fun getRecordDetailById(id: String): GameRecord
}