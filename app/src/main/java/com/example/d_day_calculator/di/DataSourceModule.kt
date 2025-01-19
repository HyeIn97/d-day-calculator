package com.example.d_day_calculator.di

import com.example.data.room.dao.DayDao
import com.example.data.room.datasource.DayDataSource
import com.example.data.room.datasource.DayDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {
    @Singleton
    @Provides
    fun provideDayDataSource(dao: DayDao): DayDataSource = DayDataSourceImpl(dao)
}