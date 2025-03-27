package com.bongpal.yatzee.core.local.di

import android.content.Context
import androidx.room.Room
import com.bongpal.yatzee.core.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "yatzee_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun providesGameRecordDao(appDatabase: AppDatabase) = appDatabase.gameRecordDao()
}