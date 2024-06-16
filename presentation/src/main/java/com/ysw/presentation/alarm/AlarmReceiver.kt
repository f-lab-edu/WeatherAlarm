package com.ysw.presentation.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.ysw.presentation.AlarmActivity

/**
 * Alarm receiver
 * 알람이 울릴 떄 행동 해야 할 역할을 정의
 */
class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        val alarmIntent = Intent(context, AlarmActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        context?.startActivity(alarmIntent)
    }
}