package com.example.data.room.repository

import com.example.data.room.datasource.DayDataSource
import com.example.data.room.entity.DayEntity
import com.example.domain.model.DayModel
import com.example.domain.repository.DayDataRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class DayDataRepositoryImpl @Inject constructor(private val dataSource: DayDataSource) : DayDataRepository {
    override suspend fun insertDay(day: DayModel): Int {
        val success = dataSource.insertDay(DayEntity(title = day.title, startDay = day.startDay, endDay = day.endDay, widget = day.isWidget, isInclude = day.isInclude)).first()
        return success
    }
}