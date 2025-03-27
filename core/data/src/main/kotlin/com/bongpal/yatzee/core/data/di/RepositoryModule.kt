package com.bongpal.yatzee.core.data.di

import com.bongpal.yatzee.core.data.repository.DefaultGameRecordRepository
import com.bongpal.yatzee.core.domain.repository.GameRecordRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindGameRecordRepository(gameRecordRepository: DefaultGameRecordRepository): GameRecordRepository
}