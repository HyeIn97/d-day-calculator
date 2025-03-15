package com.example.data.room.datasource

import com.example.data.room.dao.DayDao
import com.example.data.room.entity.DayEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class DayDataSourceImpl @Inject constructor(private val dao: DayDao) : DayDataSource {
    private val job = CoroutineScope(Dispatchers.IO)

    override suspend fun insertDay(day: DayEntity): Flow<Int> = callbackFlow {
        job.launch {
            val success = dao.insertDay(day)
            trySend(success.toInt())
        }

        awaitClose()
    }

    override suspend fun getAllDay(): Flow<List<DayEntity>> = flow {
        dao.getAllDay().collect {
            emit(it)
        }
    }

    override suspend fun deleteDay(key: Int): Flow<Int> = callbackFlow {
        job.launch {
            val success = dao.deleteDay(key)
            trySend(success)
        }

        awaitClose()
    }

    override suspend fun updateDay(day: DayEntity): Flow<Int> = callbackFlow {
        job.launch {
            val success = dao.updateDay(day)
            trySend(1)
        }

        awaitClose()
    }

    override suspend fun getNotificationCount(): Flow<Int> = callbackFlow {
        job.launch {
            val count = dao.getNotificationCount(true)
            trySend(count)
        }

        awaitClose()
    }
}