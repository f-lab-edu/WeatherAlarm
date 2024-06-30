package com.ysw.data.repository

import com.ysw.data.datasource.LocalAlarmDataSource
import com.ysw.data.di.AlarmAppDispatchers
import com.ysw.data.di.Dispatcher
import com.ysw.data.entity.mapper.asDomain
import com.ysw.data.entity.mapper.asEntity
import com.ysw.domain.Alarm
import com.ysw.domain.repository.AlarmRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.time.LocalTime
import javax.inject.Inject

class AlarmRepositoryImpl @Inject constructor (
    private val localDatasource: LocalAlarmDataSource,
    @Dispatcher(AlarmAppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : AlarmRepository {
    override fun getAllAlarm(): Flow<List<Alarm>> {
        return localDatasource.getAllAlarms().map { alarmEntityList ->
            alarmEntityList.map { alarmEntity -> alarmEntity.asDomain() }
        }.flowOn(ioDispatcher)
    }

    override suspend fun getAlarm(time: LocalTime): Alarm {
        return withContext(ioDispatcher) {
            localDatasource.getAlarm(time).asDomain()
        }
    }

    override suspend fun insertAlarm(alarm: Alarm) {
        return withContext(ioDispatcher) {
            localDatasource.insertAlarm(alarm.asEntity())
        }
    }

    override suspend fun deleteAlarm(time: LocalTime) {
        return withContext(ioDispatcher) {
            localDatasource.deleteAlarm(time)
        }
    }

    override suspend fun onOffAlarm(isOn: Boolean, time: LocalTime) {
        return withContext(ioDispatcher) {
            localDatasource.onOffAlarm(isOn, time)
        }
    }
}