package com.example.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.data.room.entity.DayEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DayDao {
    @Insert
    fun insertDay(day: DayEntity): Long

    @Query("SELECT * FROM days ORDER BY `key` DESC")
    fun getAllDay(): Flow<List<DayEntity>>

    @Query("DELETE FROM days WHERE title = :title")
    fun deleteDay(title: String)

    @Update
    fun updateDay(day: DayEntity)
}