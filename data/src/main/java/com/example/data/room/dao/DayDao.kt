package com.example.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.data.room.entity.DayEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DayDao {
    @Insert
    fun insertDay(day: DayEntity): Long

    @Query("SELECT * FROM days ORDER BY `key` DESC")
     fun getAllDay(): Flow<List<DayEntity>>
}