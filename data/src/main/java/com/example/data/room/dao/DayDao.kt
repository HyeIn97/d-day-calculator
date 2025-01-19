package com.example.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.data.room.entity.DayEntity

@Dao
interface DayDao {
    @Insert
    fun insertDay(day: DayEntity): Long
}