package com.ysw.domain

import android.net.Uri
import java.time.LocalTime

data class Alarm (
    val time : LocalTime = LocalTime.now(),
    val alarmDayList : List<String> = emptyList(),
    val volume : Float = 0.0f,
    val isOn : Boolean = true,
    val musicListByWeather : Map<String, Uri> = emptyMap()
)