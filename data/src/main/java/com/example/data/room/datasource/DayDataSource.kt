package com.example.data.room.datasource

import com.example.data.room.entity.DayEntity
import kotlinx.coroutines.flow.Flow

interface DayDataSource {
    suspend fun insertDay(day: DayEntity): Flow<Int>
    suspend fun getAllDay(): Flow<List<DayEntity>>
    suspend fun deleteDay(key: Int): Flow<Int>
    suspend fun updateDay(day: DayEntity): Flow<Int>
    suspend fun getNotificationCount(): Flow<Int>
    suspend fun getNotificationDay(): Flow<List<DayEntity>>
}