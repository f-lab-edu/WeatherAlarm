package com.ysw.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ysw.data.entity.AlarmEntity

@Database(entities = [AlarmEntity::class], version = 1)
@TypeConverters(value = [AlarmConverters::class])
abstract class AlarmDatabase : RoomDatabase() {
    abstract fun alarmDao(): AlarmDao

    companion object {
        const val name = "alarm.db"
    }
}