package com.ysw.domain.repository

import com.ysw.domain.Alarm
import kotlinx.coroutines.flow.Flow
import java.time.LocalTime


interface AlarmRepository {

    fun getAllAlarms(): Flow<List<Alarm>>
    suspend fun getAlarm(time: LocalTime): Alarm
    suspend fun insertAlarm(alarm: Alarm)
    suspend fun deleteAlarm(time: LocalTime)
    suspend fun setOnOffAlarm(isOn: Boolean, time: LocalTime)
    suspend fun isAlarmExist(time: LocalTime): Int
}