package com.ysw.domain.usecase

import com.ysw.domain.Alarm
import com.ysw.domain.repository.AlarmRepository
import java.time.LocalTime
import javax.inject.Inject

class GetAlarmUseCase @Inject constructor (private val alarmRepository: AlarmRepository) {
    suspend operator fun invoke(time: LocalTime): Alarm {
        return alarmRepository.getAlarm(time)
    }
}