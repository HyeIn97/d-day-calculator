package com.example.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "days")
data class DayEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "key") val key: Int = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "start_day") val startDay: String,
    @ColumnInfo(name = "end_day") val endDay: String,
    @ColumnInfo(name = "widget") val widget: Boolean,
    @ColumnInfo(name = "is_include") val isInclude: Boolean
)