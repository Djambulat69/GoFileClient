package com.djambulat69.gofileclient.ui

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.djambulat69.gofileclient.GoFileApp
import com.djambulat69.gofileclient.R
import com.djambulat69.gofileclient.utils.Progress

object GoFileNotificationManager {

    private const val UPLOADING_FILES_CHANNEL_ID = "uploading files channel"

    init {
        createUploadingFilesChannel()
    }

    fun buildUploadingFileNotification(context: Context, fileName: String, progress: Progress? = null): Notification {
        val contentText = context.getString(R.string.uploading_file_with_name, fileName)

        return NotificationCompat.Builder(context, UPLOADING_FILES_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_file_24)
            .setContentTitle(context.getString(R.string.uploading_file))
            .setContentText(contentText)
            .setStyle(NotificationCompat.BigTextStyle().bigText(contentText))
            .setProgress(progress?.max ?: 0, progress?.current ?: 0, progress == null)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
    }

    private fun createUploadingFilesChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            with(GoFileApp.applicationContext()) {
                val name = getString(R.string.uploading_files)
                val importance = NotificationManager.IMPORTANCE_LOW
                val channel = NotificationChannel(UPLOADING_FILES_CHANNEL_ID, name, importance)

                val notificationManager: NotificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }
        }
    }

}
