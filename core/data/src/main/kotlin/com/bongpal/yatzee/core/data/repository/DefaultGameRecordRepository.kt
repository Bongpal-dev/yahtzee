package com.bongpal.yatzee.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.bongpal.yatzee.core.domain.repository.GameRecordRepository
import com.bongpal.yatzee.core.local.api.LocalGameRecordDataSource
import com.bongpal.yatzee.core.local.mapper.toModel
import com.bongpal.yatzee.core.model.GameRecord
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultGameRecordRepository @Inject constructor(
    private val localGameRecordDataSource: LocalGameRecordDataSource
) : GameRecordRepository {
    override fun getRecords(): Flow<PagingData<GameRecord>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { localGameRecordDataSource.getRecords() }
        ).flow.map { pagingData -> pagingData.map { it.toModel() } }
    }

    override suspend fun saveRecord(gameRecord: GameRecord) {
        localGameRecordDataSource.insertRecord(gameRecord)
    }

    override suspend fun getRecordDetailById(id: String): GameRecord {
        return localGameRecordDataSource.getRecordDetailById(id)
    }
}