package com.example.domain.usecase

import com.example.domain.model.DayModel
import com.example.domain.repository.DayDataRepository

class UpdateDayUseCase(private val repository: DayDataRepository) {
    suspend operator fun invoke(day: DayModel) = repository.updateDay(day)
}