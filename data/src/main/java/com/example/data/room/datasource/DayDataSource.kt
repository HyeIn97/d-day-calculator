package com.example.data.room.datasource

import com.example.data.room.entity.DayEntity
import kotlinx.coroutines.flow.Flow

interface DayDataSource {
    suspend fun insertDay(day: DayEntity): Flow<Int>
}