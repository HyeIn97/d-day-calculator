package com.example.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.DayModel
import com.example.domain.usecase.GetNotificationCountUseCase
import com.example.domain.usecase.InsertDayUseCase
import com.example.domain.usecase.UpdateDayUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DayViewModel @Inject constructor(
    private val insertDayUseCase: InsertDayUseCase,
    private val updateDayUseCase: UpdateDayUseCase,
    private val getNotificationCountUseCase: GetNotificationCountUseCase
) : ViewModel() {
    var isNotificationPossible = false

    private val _insertDay = MutableStateFlow<Int?>(null)
    val insertDay = _insertDay.asStateFlow()

    private val _updateDay = MutableStateFlow<Int?>(null)
    val updateDay = _updateDay.asStateFlow()

    private val _notificationCount = MutableStateFlow<Boolean?>(null)
    val notificationCount = _notificationCount.asStateFlow()

    fun insertDay(day: DayModel) = viewModelScope.launch {
        val success = insertDayUseCase(day)
        _insertDay.emit(success)
    }

    fun updateDay(day: DayModel) = viewModelScope.launch {
        val success = updateDayUseCase(day).first()
        _updateDay.emit(success)
    }

    fun getNotificationCount() = viewModelScope.launch {
        getNotificationCountUseCase().collect {
            _notificationCount.emit(3 > it)
        }
    }
}