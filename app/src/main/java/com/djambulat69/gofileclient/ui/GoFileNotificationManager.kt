package com.djambulat69.gofileclient.ui

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.djambulat69.gofileclient.GoFileApp
import com.djambulat69.gofileclient.R

object GoFileNotificationManager {

    private const val UPLOADING_FILES_CHANNEL_ID = "uploading files channel"

    init {
        createUploadingFilesChannel()
    }

    fun buildUploadingFileNotification(context: Context, fileName: String): Notification {
        val contentText = context.getString(R.string.uploading_file_with_name, fileName)

        return NotificationCompat.Builder(context, UPLOADING_FILES_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_file_24)
            .setContentTitle(context.getString(R.string.uploading_file))
            .setContentText(contentText)
            .setStyle(NotificationCompat.BigTextStyle().bigText(contentText))
            .setProgress(0, 0, true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
    }

    fun buildFinishedUploadingNotification(context: Context, fileName: String, link: String): Notification {
        val contentText = context.getString(R.string.link, link)

        return NotificationCompat.Builder(context, UPLOADING_FILES_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_file_24)
            .setContentTitle(context.getString(R.string.finished_uploading, fileName))
            .setContentText(contentText)
            .setStyle(NotificationCompat.BigTextStyle().bigText(contentText))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
    }

    fun buildUploadingErrorNotification(context: Context): Notification {
        return NotificationCompat.Builder(context, UPLOADING_FILES_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_error_24)
            .setContentTitle(context.getString(R.string.smth_went_wrong))
            .setContentText(context.getString(R.string.try_again_later))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
    }

    private fun createUploadingFilesChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            with(GoFileApp.applicationContext()) {
                val name = getString(R.string.uploading_files)
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel(UPLOADING_FILES_CHANNEL_ID, name, importance)

                val notificationManager: NotificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }
        }
    }

}
