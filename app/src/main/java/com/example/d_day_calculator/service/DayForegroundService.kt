package com.example.d_day_calculator.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.d_day_calculator.R

class DayForegroundService : Service() {
    private val CHANEL_ID = "1001"
    private val CHANEL_NAME = "DAY_CHANEL"
    private var notificationManager: NotificationManager? = null
    private var notificationChannel: NotificationChannel? = null
    private var notificationBuilder: NotificationCompat.Builder? = null

    override fun onBind(p0: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()

        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationChannel =
            NotificationChannel(CHANEL_ID, CHANEL_NAME, NotificationManager.IMPORTANCE_NONE)
        notificationBuilder = NotificationCompat.Builder(this@DayForegroundService, CHANEL_ID)
            .setSmallIcon(R.drawable.ic_d)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        return START_STICKY
    }

    fun addDayNotification() {

    }
}