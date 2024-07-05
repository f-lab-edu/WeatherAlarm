package com.ysw.data.entity.mapper

import com.ysw.data.entity.AlarmEntity
import com.ysw.domain.Alarm

object AlarmEntityListMapper : EntityMapper<List<Alarm>, List<AlarmEntity>>{
    override fun asEntity(domain: List<Alarm>): List<AlarmEntity> {
        return domain.map {
            AlarmEntity(
                time = it.time,
                alarmDayList = it.alarmDayList,
                volume = it.volume,
                isOn = it.isOn,
                musicListByWeather = it.musicListByWeather
            )
        }
    }

    override fun asDomain(entity: List<AlarmEntity>): List<Alarm> {
        return entity.map {
            Alarm(
                time = it.time,
                alarmDayList = it.alarmDayList,
                volume = it.volume,
                isOn = it.isOn,
                musicListByWeather = it.musicListByWeather
            )
        }
    }
}

fun List<Alarm>.asEntity() : List<AlarmEntity> = AlarmEntityListMapper.asEntity(this)

fun List<AlarmEntity>.asDomain() : List<Alarm> = AlarmEntityListMapper.asDomain(this)