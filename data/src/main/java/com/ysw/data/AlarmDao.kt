package com.ysw.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ysw.data.entity.AlarmEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalTime


/**
 * Alarm dao
 *
 * @constructor Create empty Alarm dao
 */
@Dao
interface AlarmDao {

    @Query("SELECT * FROM alarm ORDER BY time ASC")
    fun getAllAlarm() : Flow<List<AlarmEntity>>

    @Query("SELECT * FROM alarm WHERE time = :time")
    suspend fun getAlarm(time: LocalTime) : AlarmEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlarm(alarm: AlarmEntity)

    @Query("DELETE FROM alarm WHERE time = :time")
    suspend fun deleteAlarm(time: LocalTime)

    @Query("UPDATE alarm SET isOn = :isOn WHERE time = :time")
    suspend fun turnOffAlarm(isOn: Boolean, time: LocalTime)

}