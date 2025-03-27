package com.bongpal.yatzee.core.local.di

import com.bongpal.yatzee.core.local.api.LocalGameRecordDataSource
import com.bongpal.yatzee.core.local.datasource.DefaultLocalGameRecordDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class DataSourceModule {
    @Binds
    abstract fun bindLocalDataSource(recordDataSource: DefaultLocalGameRecordDataSource): LocalGameRecordDataSource
}