package id.ac.ui.cs.mobileprogramming.hira.youngsil.utils

import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import id.ac.ui.cs.mobileprogramming.hira.youngsil.BuildConfig
import id.ac.ui.cs.mobileprogramming.hira.youngsil.MainActivity


internal class NotificationCreator(private val mContext: Context, private val todos: String) {
    fun createNotification() {
        val youngSilIntent = Intent(mContext, MainActivity::class.java)
        youngSilIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val pendingIntent = PendingIntent.getActivity(
            mContext,
            0 /* Request code */, youngSilIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val mBuilder: NotificationCompat.Builder =
            NotificationCompat.Builder(mContext, NOTIFICATION_CHANNEL_ID)
        mBuilder.setSmallIcon(R.drawable.ic_dialog_info).setContentTitle("Selamat Pagi")
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText("Berikut adalah tugas mu hari ini\n$todos")
            )
            .setAutoCancel(false)
            .setContentIntent(pendingIntent)
        val mNotificationManager =
            mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "NOTIFICATION_CHANNEL_NAME",
                importance
            )
            notificationChannel.enableLights(true)
            notificationChannel.enableVibration(true)
            notificationChannel.vibrationPattern =
                longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID)
            mNotificationManager.createNotificationChannel(notificationChannel)
        }
        mNotificationManager.notify(0 /* Request Code */, mBuilder.build())
    }

    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "10001"
    }

}