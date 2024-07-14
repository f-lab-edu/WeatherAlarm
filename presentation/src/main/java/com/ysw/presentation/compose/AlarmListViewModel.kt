package com.ysw.presentation.compose

import androidx.lifecycle.ViewModel
import com.ysw.domain.usecase.DeleteAlarmUseCase
import com.ysw.domain.usecase.GetAllAlarmsUseCase
import com.ysw.domain.usecase.SetOnOffAlarmUseCase
import com.ysw.presentation.utilities.launchInScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class AlarmListViewModel @Inject constructor(
    private val getAllAlarmsUseCase: GetAllAlarmsUseCase,
    private val deleteAlarmUseCase: DeleteAlarmUseCase,
    private val setonAlarmUseCase: SetOnOffAlarmUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<List<AlarmListUi>>(emptyList())
    val uiState: StateFlow<List<AlarmListUi>> = _uiState


    init {
        setAlarmListUi()
    }

    private fun setAlarmListUi() {
        launchInScope {
            getAllAlarmsUseCase().collect { alarmList ->
                _uiState.value = alarmList.map {
                    AlarmListUi(
                        id = it.id,
                        time = it.time,
                        alarmList = it.alarmDayList,
                        isOn = it.isOn
                    )
                }
            }
        }
    }


    fun deleteAlarm(id: Int) {
        launchInScope {
            deleteAlarmUseCase(id)
        }
    }

    fun setOnOffAlarm(isOn: Boolean, id: Int) {
        launchInScope {
            setonAlarmUseCase(isOn, id)
        }
    }

}