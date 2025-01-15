package com.example.d_day_calculator.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DayEntity(
    @PrimaryKey(autoGenerate = true) val key: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "start_day") val startDay: String,
    @ColumnInfo(name = "end_day") val endDay: String,
    @ColumnInfo(name = "widget") val widget: Boolean,
    @ColumnInfo(name = "is_include") val isInclude: Boolean
)