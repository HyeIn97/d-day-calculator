package com.example.presentation.service

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@AndroidEntryPoint
class DayAlarmController @Inject constructor(@ApplicationContext private val context: Context) {
    fun startAlarm(intent: Intent) {
        if (isExactAlarm) {
            context.startForegroundService(intent)

            val cal = java.util.Calendar.getInstance()
            cal.timeInMillis = System.currentTimeMillis()
            cal.set(java.util.Calendar.HOUR_OF_DAY, 0)
            cal.set(java.util.Calendar.MINUTE, 0)
            cal.set(java.util.Calendar.SECOND, 0)

            if (cal.timeInMillis <= System.currentTimeMillis()) cal.add(java.util.Calendar.DAY_OF_MONTH, 1)

            PendingIntent.getBroadcast(
                context,
                2000,
                Intent(context.applicationContext, DayBroadcastReceiver::class.java),
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            ).also {
                val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                val alarmClock = AlarmManager.AlarmClockInfo(cal.timeInMillis, it)
                alarmManager.setAlarmClock(alarmClock, it)
            }
        }
    }

    val isExactAlarm = ContextCompat.getSystemService(context, AlarmManager::class.java)?.canScheduleExactAlarms() ?: false
}