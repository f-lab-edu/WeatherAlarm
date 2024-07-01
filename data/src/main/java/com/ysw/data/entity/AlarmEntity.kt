package com.ysw.data.entity

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalTime

/**
 * Alarm
 *
 * @property time 알람시간
 * @property alarmDayList 알람이 울리는 요일목록
 * @property volume 음악 볼륨
 * @property isOn 알람 켜짐 여부
 * @property musicListByWeather 날씨에 따른 음악
 */
@Entity(tableName = "alarm")
data class AlarmEntity(

    @PrimaryKey
    val time : LocalTime = LocalTime.now(),
    @ColumnInfo(name = "alarm_list")
    val alarmDayList : List<String> = emptyList(),
    val volume : Float = 0.0f,
    val isOn : Boolean = false,
    @ColumnInfo(name = "music_list_by_weather")
    val musicListByWeather : Map<String, Uri> = emptyMap()
)
