package com.example.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.DayModel
import com.example.domain.usecase.InsertDayUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DayViewModel @Inject constructor(
    private val insertDayUseCase: InsertDayUseCase
) : ViewModel() {
    private val _insertDay = MutableStateFlow<Int?>(null)
    val insertDay = _insertDay.asStateFlow()

    fun insertDay(day: DayModel) = viewModelScope.launch {
        val success = insertDayUseCase(day)
        _insertDay.emit(success)
    }
}