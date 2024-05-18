package cz.cvut.fel.sit.pda.services

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder

/**
 * NotificationService is a background service that schedules a notification
 * to be shown after a specified delay.
 */
class NotificationService : Service() {

    companion object {
        const val CHANNEL_ID = "channel_reminder"
        const val NOTIFICATION_ID = 1
        const val NOTIFICATION_DELAY_MS = 6000L // 6 seconds
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    /**
     * Called when the service is started with startService().
     *
     * @param intent The Intent supplied to startService(), as given.
     * @param flags Additional data about this start request.
     * @param startId A unique integer representing this specific request to start.
     * @return The return value indicates how the system should continue the service
     *         in case it is killed: always returns START_NOT_STICKY.
     */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        scheduleNotification()
        return START_NOT_STICKY
    }

    /**
     * Schedules a notification to be triggered after a specified delay.
     *
     * The notification is scheduled using the AlarmManager to trigger after
     * 10 seconds (6000 milliseconds).
     */
    @SuppressLint("ServiceCast")
    private fun scheduleNotification() {
        val notificationIntent = Intent(this, NotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val futureInMillis = System.currentTimeMillis() + NOTIFICATION_DELAY_MS
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.set(AlarmManager.RTC_WAKEUP, futureInMillis, pendingIntent)
    }
}

