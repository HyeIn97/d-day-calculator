package com.example.presentation.receiver

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.domain.model.DayModel
import com.example.domain.usecase.GetNotificationDayUseCase
import com.example.presentation.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class DayForegroundService : Service() {

    @Inject
    lateinit var getNotificationDay: GetNotificationDayUseCase

    private val CHANEL_ID = "10001"
    private val CHANEL_NAME = "DAY_CHANEL"
    private val job = CoroutineScope(Dispatchers.IO)

    override fun onBind(p0: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()

        getDays()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (isNotificationPermission()) {
            intent?.let {
                val newDay = it.getSerializableExtra("day", DayModel::class.java)
                newDay?.let { day ->
                    createChannel(day)
                } ?: run {
                    return START_REDELIVER_INTENT
                }
            }
            return START_STICKY
        } else {
            return START_NOT_STICKY
        }
    }

    private fun createChannel(item: DayModel) = job.launch {
        val day: Int
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
        val endDay = dateFormat.parse(item.endDay)?.time
        val today = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }.time.time
        day = ((today - endDay!!) / (60 * 60 * 24 * 1000)).toInt()

        val dayTxt = if (day > 0) "D+$day" else if (day == 0) "D+0" else "D" + day.toString()

        val notification = NotificationCompat.Builder(this@DayForegroundService, CHANEL_ID)
            .setForegroundServiceBehavior(NotificationCompat.FOREGROUND_SERVICE_IMMEDIATE)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.ic_app))
            .setSmallIcon(R.drawable.ic_app)
            .setContentTitle(dayTxt)
            .setContentText(item.title)
            .setColor(ContextCompat.getColor(this@DayForegroundService, R.color.black))
            .setOngoing(true)
            .setShowWhen(false)
            .setAutoCancel(false)

        val channel = NotificationChannel(CHANEL_ID, CHANEL_NAME, IMPORTANCE_HIGH).apply {
            setShowBadge(false)
        }

        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
        manager.notify(item.key, notification.build())
    }

    private fun getDays() = job.launch {
        val notificationDay = getNotificationDay().first()
        notificationDay.map { day ->
            createChannel(day)
        }
    }

    @SuppressLint("InlinedApi")
    private fun isNotificationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
    }
}