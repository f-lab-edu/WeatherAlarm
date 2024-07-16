package com.ysw.domain.usecase

import com.ysw.domain.repository.AlarmRepository
import javax.inject.Inject

class SetOnOffAlarmUseCase @Inject constructor(private val alarmRepository: AlarmRepository) {
    suspend operator fun invoke(isOn: Boolean, id: Int) = alarmRepository.setOnOffAlarm(isOn, id)
}