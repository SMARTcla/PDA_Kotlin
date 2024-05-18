package cz.cvut.fel.sit.pda.services

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import cz.cvut.fel.sit.pda.R

/**
 * NotificationReceiver is a BroadcastReceiver that handles the triggering of notifications.
 *
 * This receiver is activated by an alarm set by the NotificationService to display a reminder
 * notification to the user.
 */
class NotificationReceiver : BroadcastReceiver() {

    companion object {
        const val CONTENT_TITLE = "Reminder"
        const val CONTENT_TEXT = "Please back to App"
    }

    /**
     * Called when the BroadcastReceiver is receiving an Intent broadcast.
     *
     * @param context The Context in which the receiver is running.
     * @param intent The Intent being received.
     */
    override fun onReceive(context: Context?, intent: Intent?) {
        val notificationManager =
            context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val builder = NotificationCompat.Builder(context, NotificationService.CHANNEL_ID)
            .setSmallIcon(R.drawable.notification)
            .setContentTitle(CONTENT_TITLE)
            .setContentText(CONTENT_TEXT)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        notificationManager.notify(NotificationService.NOTIFICATION_ID, builder.build())
    }
}