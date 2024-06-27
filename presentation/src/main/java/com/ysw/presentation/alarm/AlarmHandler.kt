package com.ysw.presentation.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import java.util.Calendar


/**
 * Alarm handler
 *
 * @param context
 */

class AlarmHandler() {

    private lateinit var context: Context

    fun setContext(context: Context) {
        this.context = context
    }

    private val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
    private val alarmIntent = Intent(context, AlarmReceiver::class.java)

    fun setAlarm(
        setDays: List<Int>,
        hour: Int,
        minute: Int,
    ) {

        val requestCode = "${hour}${minute}".toInt()
        val pendingIntent =
            PendingIntent.getBroadcast(context, requestCode, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        setDays.forEach { day ->
            val calendar = Calendar.getInstance()
            calendar.apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.DAY_OF_WEEK, day)
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, minute)
                set(Calendar.SECOND, 0)
            }

            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY * 7,
                pendingIntent
            )
        }
    }

    fun cancelAlarm(
        setDays: List<Int>,
        hour: Int,
        minute: Int,
    ) {
        val requestCode = "${hour}${minute}".toInt()
        val pendingIntent =
            PendingIntent.getBroadcast(context, requestCode, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        setDays.forEach{
            alarmManager.cancel(pendingIntent)
        }
    }

}
