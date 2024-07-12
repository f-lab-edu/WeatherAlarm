package com.ysw.domain.usecase

import com.ysw.domain.repository.AlarmRepository
import javax.inject.Inject


class DeleteAlarmUseCase @Inject constructor(private val alarmRepository: AlarmRepository) {
    suspend operator fun invoke(id: Int) = alarmRepository.deleteAlarm(id)
}