package com.ysw.domain.usecase

import com.ysw.domain.repository.AlarmRepository
import java.time.LocalTime
import javax.inject.Inject


class DeleteAlarmUseCase @Inject constructor (private val alarmRepository: AlarmRepository) {
    suspend operator fun invoke(time: LocalTime) {
        alarmRepository.deleteAlarm(time)
    }
}