package com.example.domain.usecase

import com.example.domain.repository.DayDataRepository

class GetAllDayUseCase(private val repository: DayDataRepository) {
    suspend operator fun invoke() = repository.getAllDay()
}