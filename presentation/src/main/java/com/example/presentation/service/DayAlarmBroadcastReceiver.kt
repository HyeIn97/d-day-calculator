package com.example.presentation.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DayAlarmBroadcastReceiver : BroadcastReceiver() {
    @Inject
    lateinit var alarmController: DayAlarmController

    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let {
            val alarmIntent = Intent(context, DayForegroundService::class.java).apply {
                putExtra("isReboot", false)
                putExtra("isAlarm", true)
            }

            alarmController.startAlarm(alarmIntent)
        }
    }
}