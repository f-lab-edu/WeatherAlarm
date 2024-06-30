package com.ysw.data.datasource

import com.ysw.data.entity.AlarmEntity
import com.ysw.data.AlarmDao
import kotlinx.coroutines.flow.Flow
import java.time.LocalTime
import javax.inject.Inject

class LocalAlarmDataSource @Inject constructor (private val alarmDao: AlarmDao) {
    fun getAllAlarms(): Flow<List<AlarmEntity>> = alarmDao.getAllAlarm()
    suspend fun getAlarm(time: LocalTime): AlarmEntity = alarmDao.getAlarm(time)
    suspend fun insertAlarm(alarm: AlarmEntity) = alarmDao.insertAlarm(alarm)
    suspend fun deleteAlarm(time: LocalTime) = alarmDao.deleteAlarm(time)
    suspend fun turnOffAlarm(isOn: Boolean, time: LocalTime) = alarmDao.turnOffAlarm(isOn, time)
}