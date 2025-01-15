package com.example.d_day_calculator.data.room.database

import androidx.room.Database
import com.example.d_day_calculator.data.room.dao.DayDao
import com.example.d_day_calculator.data.room.entity.DayEntity

@Database(entities = [DayEntity::class], version = 1)
abstract class DayDataBase {
    abstract fun dayDao(): DayDao
}