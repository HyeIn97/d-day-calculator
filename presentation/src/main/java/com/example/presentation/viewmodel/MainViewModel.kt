package com.example.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.DayModel
import com.example.domain.usecase.DeleteDayUseCase
import com.example.domain.usecase.GetAllDayUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val getAllDayUseCase: GetAllDayUseCase, private val deleteDayUseCase: DeleteDayUseCase) : ViewModel() {
    private val _days = MutableStateFlow<ArrayList<DayModel>?>(null)
    val days = _days.asStateFlow()

    private val _deletePosition = MutableStateFlow<Int?>(null)
    val deletePosition = _deletePosition.asStateFlow()

    fun getAllDay() = viewModelScope.launch {
        val allDay = getAllDayUseCase().first()
        _days.emit(allDay)
    }

    fun deleteDay(key: Int, position: Int) = viewModelScope.launch {
        deleteDayUseCase(key).collect {
            _deletePosition.emit(position)
        }
    }
}