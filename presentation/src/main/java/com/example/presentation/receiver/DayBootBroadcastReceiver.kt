package com.example.presentation.receiver

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class DayBootBroadcastReceiver : BroadcastReceiver() {
    @Inject
    lateinit var dayAlarmController: DayAlarmController

    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            if ("BOOT_COMPLETED" == intent?.action) {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                    CoroutineScope(Dispatchers.IO).launch {
                        val bootIntent = Intent(context, DayForegroundService::class.java).apply {
                            putExtra("isReboot", true)
                            putExtra("isAlarm", false)
                        }

                        dayAlarmController.startAlarm(bootIntent)
                    }
                }
            }
        }
    }
}