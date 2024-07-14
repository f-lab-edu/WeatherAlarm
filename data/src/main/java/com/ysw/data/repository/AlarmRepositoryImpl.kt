package com.ysw.data.repository

import com.ysw.data.source.local.LocalAlarmDataSource
import com.ysw.data.di.AlarmAppDispatchers
import com.ysw.data.di.Dispatcher
import com.ysw.data.entity.mapper.asDomain
import com.ysw.data.entity.mapper.asEntity
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


    override suspend fun getAlarm(time: LocalTime): Alarm {
        return runWithDispatcher {
            localDatasource.getAlarm(time).asDomain()
        }
    }

    override suspend fun insertAlarm(alarm: Alarm) {
        return runWithDispatcher {
            localDatasource.insertAlarm(alarm.asEntity())
        }
    }

    override suspend fun deleteAlarm(time: LocalTime) {
        return runWithDispatcher {
            localDatasource.deleteAlarm(time)
        }
    }

    override suspend fun setOnOffAlarm(isOn: Boolean, time: LocalTime) {
        return runWithDispatcher {
            localDatasource.setOnOffAlarm(isOn, time)
        }
    }

    override suspend fun isAlarmExist(time: LocalTime): Int {
        return runWithDispatcher {
            localDatasource.isAlarmExist(time)
        }
    }

     private suspend fun <T> runWithDispatcher(runFunction: suspend () -> T): T {
        return withContext(ioDispatcher) {
            runFunction()
        }
    }
}