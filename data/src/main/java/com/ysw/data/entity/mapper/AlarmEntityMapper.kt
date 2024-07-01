package com.ysw.data.entity.mapper

import com.ysw.data.entity.AlarmEntity
import com.ysw.domain.Alarm


object AlarmEntityMapper : EntityMapper<Alarm, AlarmEntity> {
    override fun asEntity(domain: Alarm): AlarmEntity {
        return AlarmEntity(
            time = domain.time,
            alarmDayList = domain.alarmDayList,
            volume = domain.volume,
            isOn = domain.isOn,
            musicListByWeather = domain.musicListByWeather
        )
    }

    override fun asDomain(entity: AlarmEntity): Alarm {
        return Alarm(
            time = entity.time,
            alarmDayList = entity.alarmDayList,
            volume = entity.volume,
            isOn = entity.isOn,
            musicListByWeather = entity.musicListByWeather
        )
    }
}

fun Alarm.asEntity() : AlarmEntity {
    return AlarmEntityMapper.asEntity(this)
}

fun AlarmEntity.asDomain() : Alarm {
    return AlarmEntityMapper.asDomain(this)
}