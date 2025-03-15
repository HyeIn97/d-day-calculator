package com.example.domain.repository

import com.example.domain.model.DayModel
import kotlinx.coroutines.flow.Flow

interface DayDataRepository {
    suspend fun insertDay(day: DayModel): Int
    suspend fun getAllDay(): Flow<ArrayList<DayModel>>
    suspend fun deleteDay(key: Int): Flow<Int>
    suspend fun updateDay(day: DayModel): Flow<Int>
}