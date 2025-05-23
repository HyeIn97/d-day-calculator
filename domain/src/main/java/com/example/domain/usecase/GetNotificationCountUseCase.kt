package com.example.domain.usecase

import com.example.domain.repository.DayDataRepository

class GetNotificationCountUseCase(private val repository: DayDataRepository) {
    suspend operator fun invoke() = repository.getNotificationCount()
}