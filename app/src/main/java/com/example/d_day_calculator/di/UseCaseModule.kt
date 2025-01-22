package com.example.d_day_calculator.di

import com.example.domain.repository.DayDataRepository
import com.example.domain.usecase.GetAllDayUseCase
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
    fun provideInsertDayUseCase(repository: DayDataRepository) = InsertDayUseCase(repository)

    @Singleton
    @Provides
    fun provideGetAllDayUseCase(repository: DayDataRepository) = GetAllDayUseCase(repository)
}