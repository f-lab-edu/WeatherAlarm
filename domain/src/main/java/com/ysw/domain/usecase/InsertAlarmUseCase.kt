package com.ysw.domain.usecase

import com.ysw.domain.Alarm
import com.ysw.domain.repository.AlarmRepository
import javax.inject.Inject

class InsertAlarmUseCase @Inject constructor (private val alarmRepository: AlarmRepository) {
    suspend operator fun invoke(alarm: Alarm) {
        alarmRepository.insertAlarm(alarm)
    }
}