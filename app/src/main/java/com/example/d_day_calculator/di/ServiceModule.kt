package com.example.d_day_calculator.di

import android.content.Context
import com.example.domain.repository.DayDataRepository
import com.example.domain.usecase.GetNotificationDayUseCase
import com.example.presentation.service.DayAlarmController
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ServiceComponent::class)
class ServiceModule {
    @Provides
    fun provideNotificationDayUseCase(repository: DayDataRepository) = GetNotificationDayUseCase(repository)

    @Provides
    fun provideAlarmController(@ApplicationContext context: Context)= DayAlarmController(context)
}