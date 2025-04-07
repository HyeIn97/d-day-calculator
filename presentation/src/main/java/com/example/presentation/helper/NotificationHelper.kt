package com.example.presentation.helper

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.Service.NOTIFICATION_SERVICE
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.domain.model.DayModel
import com.example.presentation.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class NotificationHelper @Inject constructor(private val context: Context) {
    private val CHANEL_ID = "10001"
    private val CHANEL_NAME = "DAY_CHANEL"
    private val manager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    private var notificationChannel: NotificationChannel? = null
    private val job = CoroutineScope(Dispatchers.IO)

    fun createNotify(intent: Intent?) {
        if (isNotificationPermission()) {
            intent?.let {
                val newDay = it.getSerializableExtra("day", DayModel::class.java)

                newDay?.let { day ->
                    if (hasChannel()) {
                        registerNotification(day)
                    } else {
                        createChannel(day)
                    }
                }
            }
        }
    }

    private fun hasChannel() = manager.getNotificationChannel(CHANEL_ID) != null

    private fun createChannel(item: DayModel) = job.launch {
        notificationChannel = NotificationChannel(CHANEL_ID, CHANEL_NAME, IMPORTANCE_HIGH).apply {
            setShowBadge(false)
        }

        notificationChannel?.let {
            manager.createNotificationChannel(it)
            registerNotification(item)
        }
    }

    private fun registerNotification(item: DayModel) {
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

        val notification = NotificationCompat.Builder(context, CHANEL_ID)
            .setForegroundServiceBehavior(NotificationCompat.FOREGROUND_SERVICE_IMMEDIATE)
            .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.ic_app))
            .setSmallIcon(R.drawable.ic_app)
            .setContentTitle(dayTxt)
            .setContentText(item.title)
            .setColor(ContextCompat.getColor(context, R.color.black))
            .setOngoing(true)
            .setShowWhen(false)
            .setAutoCancel(false)

        manager.notify(item.key, notification.build())
    }

    @SuppressLint("InlinedApi")
    private fun isNotificationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
    }
}