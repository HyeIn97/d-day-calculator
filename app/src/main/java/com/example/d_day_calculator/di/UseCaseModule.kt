package com.example.d_day_calculator.di

import com.example.domain.repository.DayDataRepository
import com.example.domain.usecase.InsertDayUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    @Singleton
    @Provides
    fun provideInsertDayUseCase(dayDataRepository: DayDataRepository) = InsertDayUseCase(dayDataRepository)
}