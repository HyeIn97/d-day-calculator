package com.example.data.room.repository

import com.example.data.room.datasource.DayDataSource
import com.example.data.room.entity.DayEntity
import com.example.domain.model.DayModel
import com.example.domain.repository.DayDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DayDataRepositoryImpl @Inject constructor(private val dataSource: DayDataSource) : DayDataRepository {
    override suspend fun insertDay(day: DayModel): Int {
        val success = dataSource.insertDay(DayEntity(day.key, day.title, day.insertDay, day.endDay, day.isNotification, day.isInclude)).first()
        return success
    }

    override suspend fun getAllDay(): Flow<ArrayList<DayModel>> = flow {
        dataSource.getAllDay().collect { days ->
            val list = arrayListOf<DayModel>()

            days.map { day ->
                list.add(DayModel(day.key, day.title, day.insertDay, day.endDay, day.isNotification, day.isInclude))
            }

            emit(list)
        }
    }

    override suspend fun deleteDay(key: Int): Flow<Int> = flow {
        dataSource.deleteDay(key).collect {
            emit(it)
        }
    }

    override suspend fun updateDay(day: DayModel): Flow<Int> = flow {
        val dayEntity = DayEntity(day.key, day.title, day.insertDay, day.endDay, day.isNotification, day.isInclude)
        dataSource.updateDay(dayEntity).collect {
            emit(it)
        }
    }

    override suspend fun getNotificationCount(): Flow<Int> = flow {
        dataSource.getNotificationCount().collect {
            emit(it)
        }
    }

    override suspend fun getNotificationDay(): Flow<List<DayModel>> = flow {
        dataSource.getNotificationDay().collect { days ->
            val list = arrayListOf<DayModel>()

            days.map { day ->
                list.add(DayModel(day.key, day.title, day.insertDay, day.endDay, day.isNotification, day.isInclude))
            }

            emit(list)
        }
    }
}