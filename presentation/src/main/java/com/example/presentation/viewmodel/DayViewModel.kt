package com.example.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.DayModel
import com.example.domain.usecase.InsertDayUseCase
import com.example.domain.usecase.UpdateDayUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DayViewModel @Inject constructor(
    private val insertDayUseCase: InsertDayUseCase,
    private val updateDayUseCase: UpdateDayUseCase
) : ViewModel() {
    private val _insertDay = MutableSharedFlow<Int>(replay = 1, extraBufferCapacity = 1)
    val insertDay = _insertDay.asSharedFlow()

    private val _updateDay = MutableSharedFlow<Int>(replay = 1, extraBufferCapacity = 1)
    val updateDay = _updateDay.asSharedFlow()

    fun insertDay(day: DayModel) = viewModelScope.launch {
        val success = insertDayUseCase(day)
        _insertDay.emit(success)
    }

    fun updateDay(day: DayModel) = viewModelScope.launch {
        val success = updateDayUseCase(day).first()
        _updateDay.emit(success)
    }
}