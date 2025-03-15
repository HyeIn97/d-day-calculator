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

    @Query("SELECT * FROM days ORDER BY `insert_day` DESC")
    fun getAllDay(): Flow<List<DayEntity>>

    @Query("DELETE FROM days WHERE `key` = :key")
    fun deleteDay(key: Int): Int

    @Update
    fun updateDay(day: DayEntity)

    @Query("DELETE FROM days WHERE `is_notification` = :isNotification")
    fun getNotificationCount(isNotification: Boolean): Int
}