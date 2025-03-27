package com.bongpal.yatzee.core.local.datasource

import android.util.Log
import androidx.paging.PagingSource
import com.bongpal.yatzee.core.local.api.LocalGameRecordDataSource
import com.bongpal.yatzee.core.local.dao.GameRecordDao
import com.bongpal.yatzee.core.local.entity.GameRecordEntity
import com.bongpal.yatzee.core.local.mapper.toEntity
import com.bongpal.yatzee.core.model.GameRecord
import javax.inject.Inject

class DefaultLocalGameRecordDataSource @Inject constructor(
    private val gameRecordDao: GameRecordDao
) : LocalGameRecordDataSource {
    override suspend fun insertRecord(gameRecord: GameRecord) {
        Log.i("insertTest: LDS", "insertRecord: $gameRecord")
        gameRecordDao.insertRecord(gameRecord.toEntity())
    }

    override fun getRecords(): PagingSource<Int, GameRecordEntity> {
        return gameRecordDao.getRecords()
    }
}