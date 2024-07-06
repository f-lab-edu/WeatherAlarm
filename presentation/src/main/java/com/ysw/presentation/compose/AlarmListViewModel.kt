package com.ysw.presentation.compose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ysw.domain.usecase.DeleteAlarmUseCase
import com.ysw.domain.usecase.GetAllAlarmsUseCase
import com.ysw.domain.usecase.SetOnOffAlarmUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalTime
import javax.inject.Inject


@HiltViewModel
class AlarmListViewModel @Inject constructor(
    private val getAllAlarmsUseCase : GetAllAlarmsUseCase,
    private val deleteAlarmUseCase: DeleteAlarmUseCase,
    private val setonAlarmUseCase: SetOnOffAlarmUseCase
) : ViewModel(){

    private val _uiState = MutableStateFlow<List<AlarmListUi>>(emptyList())
    val uiState: StateFlow<List<AlarmListUi>> = _uiState


    init {
        setAlarmListUi()
    }

    private fun setAlarmListUi(){
        viewModelScope.launch {
            getAllAlarmsUseCase().collect{ alarmList ->
                _uiState.value = alarmList.map {
                    AlarmListUi(
                        time = it.time,
                        alarmList = it.alarmDayList,
                        isOn = it.isOn
                    )
                }
            }
        }
    }


    fun deleteAlarm(time: LocalTime){
        viewModelScope.launch {
            deleteAlarmUseCase(time)
        }
    }

    fun setOnOffAlarm(isOn: Boolean, time: LocalTime){
        viewModelScope.launch {
            setonAlarmUseCase(isOn, time)
        }
    }


}