package com.example.data.room.database

import androidx.room.Database
import com.example.data.room.dao.DayDao
import com.example.data.room.entity.DayEntity

@Database(entities = [DayEntity::class], version = 1)
abstract class DayDataBase {
    abstract fun dayDao(): DayDao
}