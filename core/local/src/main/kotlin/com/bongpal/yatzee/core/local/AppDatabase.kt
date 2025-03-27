package com.bongpal.yatzee.core.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bongpal.yatzee.core.local.dao.GameRecordDao
import com.bongpal.yatzee.core.local.entity.GameRecordEntity

@Database(entities = [GameRecordEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun gameRecordDao(): GameRecordDao
}