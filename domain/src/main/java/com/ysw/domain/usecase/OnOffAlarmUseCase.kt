package com.ysw.domain.usecase

import com.ysw.domain.repository.AlarmRepository
import java.time.LocalTime
import javax.inject.Inject

class OnOffAlarmUseCase @Inject constructor (private val alarmRepository: AlarmRepository) {
    suspend operator fun invoke(isOn: Boolean, time: LocalTime) {
        alarmRepository.onOffAlarm(isOn, time)
    }
}