package com.example.domain.repository

import com.example.domain.model.DayModel

interface DayDataRepository {
    suspend fun insertDay(day: DayModel): Int
}