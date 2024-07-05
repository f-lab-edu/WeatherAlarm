package com.ysw.presentation.compose

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor() : ViewModel(

) {

    private val _uiState = MutableStateFlow(AlarmUiState())
    val uiState: StateFlow<AlarmUiState> = _uiState

    fun getAlarmTime(localTime: LocalTime){
        updateUiState { currentState ->
            currentState.copy(time = localTime)
        }
    }

    fun updateWeekDays(day: String) {
        val currentList = _uiState.value.alarmList.toMutableList()
        if (day in currentList) {
            currentList.remove(day)
        } else {
            currentList.add(day)
        }
        updateUiState { currentState ->
            currentState.copy(alarmList = currentList)
        }
    }

    fun getAlarmVolume(volume: Float){
        updateUiState { currentState ->
            currentState.copy(volume = volume)
        }
    }

    fun setAlarmOn(alarmOn: Boolean){
        updateUiState { currentState ->
            currentState.copy(isOn = alarmOn)
        }
    }

    fun setAlarmMusic(weather: String, music: Uri){
        val currentMap = _uiState.value.musicListByWeather.toMutableMap()
        currentMap[weather] = music
        updateUiState { currentState ->
            currentState.copy(musicListByWeather = currentMap)
        }
    }

    private inline fun updateUiState(update: (currentState: AlarmUiState) -> AlarmUiState) {
        _uiState.update { currentState ->
            update(currentState)
        }
    }
}

