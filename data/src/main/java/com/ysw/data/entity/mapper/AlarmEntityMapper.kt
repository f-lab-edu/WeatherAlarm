package com.ysw.data.entity.mapper

import com.ysw.data.entity.AlarmEntity
import com.ysw.domain.models.Alarm


object AlarmEntityMapper : EntityMapper<Alarm, AlarmEntity> {

    override fun asEntity(domain: Alarm): AlarmEntity {
        return AlarmEntity(
            id = domain.id,
            time = domain.time,
            alarmDayList = domain.alarmDayList,
            volume = domain.volume,
            isOn = domain.isOn,
            musicListByWeather = domain.musicListByWeather
        )
    }

    override fun asDomain(entity: AlarmEntity): Alarm {
        return Alarm(
            id = entity.id,
            time = entity.time,
            alarmDayList = entity.alarmDayList,
            volume = entity.volume,
            isOn = entity.isOn,
            musicListByWeather = entity.musicListByWeather
        )
    }
}


fun Alarm.asEntity(): AlarmEntity = AlarmEntityMapper.asEntity(this)

fun AlarmEntity.asDomain(): Alarm = AlarmEntityMapper.asDomain(this)
