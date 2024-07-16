package com.ysw.domain.usecase

import com.ysw.domain.Alarm
import com.ysw.domain.repository.AlarmRepository
import javax.inject.Inject

class UpdateAlarmUseCase @Inject constructor(private val alarmRepository: AlarmRepository) {
    suspend operator fun invoke(alarm: Alarm) = alarmRepository.updateAlarm(alarm)
}