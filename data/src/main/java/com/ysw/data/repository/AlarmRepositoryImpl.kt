package com.ysw.data.repository

import com.ysw.data.di.AlarmAppDispatchers
import com.ysw.data.di.Dispatcher
import com.ysw.data.entity.mapper.asDomain
import com.ysw.data.entity.mapper.asEntity
import com.ysw.data.source.local.LocalAlarmDataSource
import com.ysw.domain.models.Alarm
import com.ysw.domain.repository.AlarmRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import java.time.LocalTime
import javax.inject.Inject

class AlarmRepositoryImpl @Inject constructor(
    private val localDatasource: LocalAlarmDataSource,
    @Dispatcher(AlarmAppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : AlarmRepository {

    override fun getAllAlarms(): Flow<List<Alarm>> = flow {
        localDatasource.getAllAlarms().collect {
            emit(it.asDomain())
        }
    }.flowOn(ioDispatcher)


    override suspend fun getAlarm(id: Int): Alarm =
        runWithDispatcher(ioDispatcher) { localDatasource.getAlarm(id).asDomain() }

    override suspend fun insertAlarm(alarm: Alarm) =
        runWithDispatcher(ioDispatcher) { localDatasource.insertAlarm(alarm.asEntity()) }


    override suspend fun updateAlarm(alarm: Alarm) =
        runWithDispatcher(ioDispatcher) { localDatasource.updateAlarm(alarm.asEntity()) }


    override suspend fun deleteAlarm(id: Int) =
        runWithDispatcher(ioDispatcher) { localDatasource.deleteAlarm(id) }


    override suspend fun setOnOffAlarm(isOn: Boolean, id: Int) =
        runWithDispatcher(ioDispatcher) { localDatasource.setOnOffAlarm(isOn, id) }


    override suspend fun isAlarmExist(time: LocalTime): List<Alarm> =
        runWithDispatcher(ioDispatcher) { localDatasource.isAlarmExist(time).asDomain() }

}

