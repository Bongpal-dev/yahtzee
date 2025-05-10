package com.bongpal.yatzee.core.local.datasource

import androidx.paging.PagingSource
import com.bongpal.yatzee.core.local.api.LocalGameRecordDataSource
import com.bongpal.yatzee.core.local.dao.GameRecordDao
import com.bongpal.yatzee.core.local.entity.GameRecordEntity
import com.bongpal.yatzee.core.local.mapper.toEntity
import com.bongpal.yatzee.core.local.mapper.toModel
import com.bongpal.yatzee.core.model.GameRecord
import javax.inject.Inject

class DefaultLocalGameRecordDataSource @Inject constructor(
    private val gameRecordDao: GameRecordDao
) : LocalGameRecordDataSource {
    override suspend fun insertRecord(gameRecord: GameRecord) {
        gameRecordDao.insertRecord(gameRecord.toEntity())
    }

    override fun getRecords(): PagingSource<Int, GameRecordEntity> {
        return gameRecordDao.getRecords()
    }

    override suspend fun getRecordDetailById(id: String): GameRecord {
        return gameRecordDao.getRecordDetailById(id).toModel()
    }
}