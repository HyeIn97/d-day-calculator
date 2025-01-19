package com.example.domain.usecase

import com.example.domain.model.DayModel
import com.example.domain.repository.DayDataRepository

class InsertDayUseCase(private val dayDataRepository: DayDataRepository) {
    suspend operator fun invoke(day: DayModel) = dayDataRepository.insertDay(day)
}