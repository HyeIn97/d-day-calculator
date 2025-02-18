package com.example.domain.model

data class DayModel(
    val key: Int,
    val title: String,
    val insertDay: String,
    val endDay: String,
    val isWidget: Boolean,
    val isInclude: Boolean
)