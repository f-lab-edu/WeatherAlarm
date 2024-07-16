package com.ysw.domain.repository

import com.ysw.domain.Alarm
import kotlinx.coroutines.flow.Flow
import java.time.LocalTime


interface AlarmRepository {

    fun getAllAlarms(): Flow<List<Alarm>>
    suspend fun getAlarm(id: Int): Alarm
    suspend fun insertAlarm(alarm: Alarm)
    suspend fun updateAlarm(alarm: Alarm)
    suspend fun deleteAlarm(id: Int)
    suspend fun setOnOffAlarm(isOn: Boolean, id: Int)
    suspend fun isAlarmExist(time: LocalTime): List<Alarm>
}