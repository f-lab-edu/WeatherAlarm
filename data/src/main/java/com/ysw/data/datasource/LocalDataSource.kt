package com.ysw.data.datasource

import com.ysw.data.entity.AlarmEntity
import com.ysw.data.AlarmDao
import kotlinx.coroutines.flow.Flow
import java.time.LocalTime
import javax.inject.Inject

class LocalAlarmDataSource @Inject constructor (private val alarmDao: AlarmDao) {
    fun getAllAlarms(): Flow<List<AlarmEntity>> = alarmDao.getAllAlarms()
    suspend fun getAlarm(id: Int): AlarmEntity = alarmDao.getAlarm(id)
    suspend fun insertAlarm(alarm: AlarmEntity) = alarmDao.insertAlarm(alarm)
    suspend fun updateAlarm(alarm: AlarmEntity) = alarmDao.updateAlarm(alarm)
    suspend fun deleteAlarm(id: Int) = alarmDao.deleteAlarm(id)
    suspend fun setOnOffAlarm(isOn: Boolean, id: Int) = alarmDao.setOnOffAlarm(isOn, id)
    suspend fun isAlarmExist(time: LocalTime): List<AlarmEntity> = alarmDao.isAlarmExist(time)
}