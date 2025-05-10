package com.bongpal.yatzee.core.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bongpal.yatzee.core.local.entity.GameRecordEntity

@Dao
interface GameRecordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecord(record: GameRecordEntity)

    @Query("SELECT * FROM records ORDER BY totalScore DESC")
    fun getRecords(): PagingSource<Int, GameRecordEntity>

    @Query("SELECT * FROM records WHERE recordId = :id")
    suspend fun getRecordDetailById(id: String): GameRecordEntity
}