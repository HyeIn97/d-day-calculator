package com.example.d_day_calculator.di

import com.example.data.room.datasource.DayDataSource
import com.example.data.room.repository.DayDataRepositoryImpl
import com.example.domain.repository.DayDataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Singleton
    @Provides
    fun provideDayRepository(dataSource: DayDataSource): DayDataRepository = DayDataRepositoryImpl(dataSource)
}