package com.ysw.presentation.compose

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ysw.domain.Alarm
import com.ysw.domain.usecase.DeleteAlarmUseCase
import com.ysw.domain.usecase.GetAlarmUseCase
import com.ysw.domain.usecase.InsertAlarmUseCase
import com.ysw.domain.usecase.IsAlarmExistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalTime
import javax.inject.Inject


@HiltViewModel
class AlarmSettingViewModel @Inject constructor(
    private val getAlarmUseCase: GetAlarmUseCase,
    private val insertAlarmUseCase: InsertAlarmUseCase,
    private val isAlarmExistUseCase: IsAlarmExistUseCase,
    private val deleteAlarmUseCase: DeleteAlarmUseCase
) : ViewModel() {


    private val _uiState = MutableStateFlow(AlarmSettingUi())
    val uiState: StateFlow<AlarmSettingUi> = _uiState

    fun getAlarmTime(localTime: LocalTime) {
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

    fun getAlarmVolume(volume: Float) {
        updateUiState { currentState ->
            currentState.copy(volume = volume)
        }
    }

    fun setAlarmMusic(weather: String, music: Uri) {
        val currentMap = _uiState.value.musicListByWeather.toMutableMap()
        currentMap[weather] = music
        updateUiState { currentState ->
            currentState.copy(musicListByWeather = currentMap)
        }
    }


    fun setAlarmUi(time: LocalTime?) {
        if (time == null) {
            val defaultMusicMap: MutableMap<String, Uri> = mutableMapOf()
            WeatherCondition.entries.forEach { weather ->
                when (weather.description) {
                    WeatherCondition.CLEAR.description -> {
                        defaultMusicMap[weather.description] =
                            Uri.parse(WeatherCondition.CLEAR.musicUri)
                    }

                    WeatherCondition.RAIN.description -> {
                        defaultMusicMap[weather.description] =
                            Uri.parse(WeatherCondition.RAIN.musicUri)
                    }

                    WeatherCondition.SNOW.description -> {
                        defaultMusicMap[weather.description] =
                            Uri.parse(WeatherCondition.SNOW.musicUri)
                    }
                }
            }
            updateUiState { currentState ->
                currentState.copy(
                    musicListByWeather = defaultMusicMap
                )
            }
        } else {
            viewModelScope.launch {
                getAlarmUseCase.invoke(time).let {
                    updateUiState { currentState ->
                        currentState.copy(
                            time = it.time,
                            alarmList = it.alarmDayList,
                            volume = it.volume,
                            isOn = it.isOn,
                            musicListByWeather = it.musicListByWeather
                        )
                    }
                }
            }
        }
    }


    fun saveAlarm(oldTime: LocalTime?) {
        viewModelScope.launch {
            if (oldTime != null && isAlarmExistUseCase(oldTime) > 0) {
                deleteAlarmUseCase(oldTime)
            }
            insertAlarmUseCase.invoke(
                Alarm(
                    time = _uiState.value.time,
                    alarmDayList = _uiState.value.alarmList,
                    volume = _uiState.value.volume,
                    isOn = _uiState.value.isOn,
                    musicListByWeather = _uiState.value.musicListByWeather
                )
            )
        }
    }

    private inline fun updateUiState(update: (currentState: AlarmSettingUi) -> AlarmSettingUi) {
        _uiState.update { currentState ->
            update(currentState)
        }
    }

}