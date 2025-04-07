package com.example.d_day_calculator.di

import android.content.Context
import com.example.presentation.helper.NotificationHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext

@Module
@InstallIn(ActivityComponent::class)
class ActivityModel {
    @Provides
    fun provideCreateDayNotification(@ActivityContext context: Context) = NotificationHelper(context)
}