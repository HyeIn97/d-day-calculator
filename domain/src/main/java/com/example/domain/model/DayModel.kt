package com.example.domain.model

import java.io.Serializable

data class DayModel(
    val key: Int,
    val title: String,
    val insertDay: String,
    val endDay: String,
    val isWidget: Boolean,
    val isInclude: Boolean
): Serializable