package com.example.domain.usecase

import com.example.domain.repository.DayDataRepository

class DeleteDayUseCase(private val repository: DayDataRepository) {
    suspend operator fun invoke(key: Int) = repository.deleteDay(key)
}