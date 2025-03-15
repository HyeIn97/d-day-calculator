package com.example.d_day_calculator.di

import com.example.domain.repository.DayDataRepository
import com.example.domain.usecase.DeleteDayUseCase
import com.example.domain.usecase.GetAllDayUseCase
import com.example.domain.usecase.GetNotificationCountUseCase
import com.example.domain.usecase.GetNotificationDayUseCase
import com.example.domain.usecase.InsertDayUseCase
import com.example.domain.usecase.UpdateDayUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ServiceComponent::class)
class ServiceModule {
    @Provides
    fun provideNotificationDayUseCase(repository: DayDataRepository) = GetNotificationDayUseCase(repository)
}