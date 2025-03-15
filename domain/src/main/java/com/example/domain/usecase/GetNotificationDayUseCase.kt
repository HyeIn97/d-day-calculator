package com.example.domain.usecase

import com.example.domain.repository.DayDataRepository

class GetNotificationDayUseCase(private val repository: DayDataRepository) {
    suspend operator fun invoke() = repository.getNotificationDay()
}