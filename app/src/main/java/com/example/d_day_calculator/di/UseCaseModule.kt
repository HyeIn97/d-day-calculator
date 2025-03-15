package com.example.d_day_calculator.di

import com.example.domain.repository.DayDataRepository
import com.example.domain.usecase.DeleteDayUseCase
import com.example.domain.usecase.GetAllDayUseCase
import com.example.domain.usecase.GetNotificationCountUseCase
import com.example.domain.usecase.InsertDayUseCase
import com.example.domain.usecase.UpdateDayUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {
    @Provides
    fun provideInsertDayUseCase(repository: DayDataRepository) = InsertDayUseCase(repository)

    @Provides
    fun provideGetAllDayUseCase(repository: DayDataRepository) = GetAllDayUseCase(repository)

    @Provides
    fun provideDeleteDayUseCase(repository: DayDataRepository) = DeleteDayUseCase(repository)

    @Provides
    fun provideUpdateDayUseCase(repository: DayDataRepository) = UpdateDayUseCase(repository)

    @Provides
    fun provideNotificationCountUseCase(repository: DayDataRepository) = GetNotificationCountUseCase(repository)

    @Provides
    fun provideNotificationDayUseCase(repository: DayDataRepository) = GetNotificationCountUseCase(repository)
}