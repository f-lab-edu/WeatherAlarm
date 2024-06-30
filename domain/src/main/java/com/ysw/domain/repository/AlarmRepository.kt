package com.ysw.domain.repository

import com.ysw.domain.Alarm
import kotlinx.coroutines.flow.Flow
import java.time.LocalTime


interface AlarmRepository {

    fun getAllAlarm(): Flow<List<Alarm>>
    suspend fun getAlarm(time: LocalTime): Alarm
    suspend fun insertAlarm(alarm: Alarm)
    suspend fun deleteAlarm(time: LocalTime)
    suspend fun turnOffAlarm(isOn: Boolean, time: LocalTime)
}