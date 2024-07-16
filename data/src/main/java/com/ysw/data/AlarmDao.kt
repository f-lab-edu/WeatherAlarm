package com.ysw.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
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
    fun getAllAlarms(): Flow<List<AlarmEntity>>

    @Query("SELECT * FROM alarm WHERE id = :id")
    suspend fun getAlarm(id: Int): AlarmEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlarm(alarm: AlarmEntity)

    @Update
    suspend fun updateAlarm(alarm: AlarmEntity)

    @Query("DELETE FROM alarm WHERE id = :id")
    suspend fun deleteAlarm(id: Int)

    @Query("UPDATE alarm SET isOn = :isOn WHERE id = :id")
    suspend fun setOnOffAlarm(isOn: Boolean, id: Int)

    @Query("SELECT * FROM alarm WHERE time = :time")
    suspend fun isAlarmExist(time: LocalTime): List<AlarmEntity>

}