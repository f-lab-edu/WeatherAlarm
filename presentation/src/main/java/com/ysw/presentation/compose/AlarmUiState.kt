package com.ysw.presentation.compose

import android.net.Uri
import java.time.LocalTime

data class AlarmUiState(
    val time : LocalTime = LocalTime.now(),
    val alarmList : List<String> = emptyList(),
    val volume : Float = 0.0f,
    val isOn : Boolean = true,
    val musicListByWeather : Map<String, Uri> = emptyMap()
    )
