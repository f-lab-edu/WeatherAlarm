package com.ysw.presentation.compose

import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalTime

class AlarmViewModel : ViewModel(

) {

    private val _uiState = MutableStateFlow(AlarmUiState())
    val uiState: StateFlow<AlarmUiState> = _uiState.asStateFlow()

    fun getAlarmTime(localTime: LocalTime){
        _uiState.update { currentState ->
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
        _uiState.update { currentState ->
            currentState.copy(alarmList = currentList)
        }
    }

    fun getAlarmVolume(volume: Float){
        _uiState.update { currentState ->
            currentState.copy(volume = volume)
        }
    }

    fun setAlarmOn(alarmOn: Boolean){
        _uiState.update { currentState ->
            currentState.copy(isOn = alarmOn)
        }
    }

    fun setAlarmMusic(weather: String, music: Uri){
        val currentMap = _uiState.value.musicListByWeather.toMutableMap()
        currentMap[weather] = music
        _uiState.update { currentState ->
            currentState.copy(musicListByWeather = currentMap)
        }
    }

}