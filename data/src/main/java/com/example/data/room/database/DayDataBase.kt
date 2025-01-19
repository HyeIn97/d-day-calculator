package com.example.data.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.room.dao.DayDao
import com.example.data.room.entity.DayEntity

@Database(entities = [DayEntity::class], version = 1)
abstract class DayDataBase : RoomDatabase() {
    abstract fun dayDao(): DayDao
}