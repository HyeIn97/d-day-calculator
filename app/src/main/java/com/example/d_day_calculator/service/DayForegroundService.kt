package com.example.d_day_calculator.service

import android.app.Service
import android.content.Intent
import android.os.IBinder

class DayForegroundService : Service() {
    override fun onBind(p0: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        return START_STICKY
    }

    fun addDayNotification() {

    }
}