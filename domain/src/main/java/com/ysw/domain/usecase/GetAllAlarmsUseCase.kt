package com.ysw.domain.usecase

import com.ysw.domain.Alarm
import com.ysw.domain.repository.AlarmRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllAlarmsUseCase @Inject constructor (private val alarmRepository: AlarmRepository) {
    operator fun invoke(): Flow<List<Alarm>> {
        return alarmRepository.getAllAlarm()
    }
}