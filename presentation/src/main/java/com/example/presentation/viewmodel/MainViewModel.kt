package com.example.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.DayModel
import com.example.domain.usecase.GetAllDayUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val getAllDayUseCase: GetAllDayUseCase) : ViewModel() {
    private val _days = MutableStateFlow<ArrayList<DayModel>?>(null)
    val days = _days.asStateFlow()

    fun getAllDay() = viewModelScope.launch {
        getAllDayUseCase().collect {
            _days.emit(it)
        }
    }
}