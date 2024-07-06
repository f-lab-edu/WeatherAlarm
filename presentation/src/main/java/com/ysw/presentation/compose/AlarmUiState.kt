package com.ysw.presentation.compose

import android.net.Uri
import java.time.LocalTime

data class AlarmListUi(
    val time: LocalTime = LocalTime.now(),
    val alarmList: List<String> = emptyList(),
    val isOn: Boolean = false,
)

data class AlarmSettingUi (
    val time: LocalTime = LocalTime.now(),
    val alarmList: List<String> = emptyList(),
    val volume: Float = 0f,
    val isOn: Boolean = true,
    val musicListByWeather: Map<String, Uri> = emptyMap(),
    )