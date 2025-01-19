package com.example.d_day_calculator.di

import android.content.Context
import androidx.room.Room
import com.example.data.room.dao.DayDao
import com.example.data.room.database.DayDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {
    @Singleton
    @Provides
    fun provideDayDataBase(@ApplicationContext context: Context): DayDataBase = Room.databaseBuilder(
        context, DayDataBase::class.java, "day_db"
    ).build()

    @Singleton
    @Provides
    fun provideDayDao(dayDataBase: DayDataBase): DayDao = dayDataBase.dayDao()
}