package com.djambulat69.gofileclient.ui.uploadFile

import android.app.Service
import android.content.Intent
import android.net.Uri
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import com.djambulat69.gofileclient.db.FilesDatabase
import com.djambulat69.gofileclient.network.FileToUpload
import com.djambulat69.gofileclient.network.GoFileApiServiceHelper
import com.djambulat69.gofileclient.network.UploadFileData
import com.djambulat69.gofileclient.ui.GoFileNotificationManager
import com.djambulat69.gofileclient.utils.dispatchTo
import com.djambulat69.gofileclient.utils.disposeSafe
import com.djambulat69.gofileclient.utils.queryName
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class UploadFileService : Service() {

    private val disposable = CompositeDisposable()

    private val apiServiceHelper = GoFileApiServiceHelper
    private val filesDao = FilesDatabase.instance.filesDao()

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {

        val fileUri = intent.extras?.getParcelable<Uri>(FILE_URI_EXTRA) ?: return START_NOT_STICKY
        val fileName = contentResolver.queryName(fileUri).orEmpty()

        startForeground(
            fileUri.hashCode(),
            GoFileNotificationManager.buildUploadingFileNotification(this, fileName)
        )
        uploadFile(fileUri)

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        disposable.disposeSafe()
        super.onDestroy()
    }

    private fun uploadSuccess(file: UploadFileData) {
        stopForeground(true)
        notifySuccess(file)
        stopSelf()
    }

    private fun uploadError(e: Throwable) {
        stopForeground(true)
        notifyError(e.hashCode())
        stopSelf()
    }

    private fun uploadFile(uri: Uri) {

        apiServiceHelper.getServer()
            .subscribeOn(Schedulers.io())
            .flatMap { getServerResponse ->
                Single.fromCallable {
                    FileToUpload(
                        fileName = contentResolver.queryName(uri).orEmpty(),
                        mimeType = contentResolver.getType(uri).orEmpty(),
                        bytes = contentResolver.openInputStream(uri).use { it?.readBytes() }!!,
                        server = getServerResponse.data.server
                    )
                }
            }
            .flatMap { fileToUpload ->
                apiServiceHelper.uploadFile(
                    fileToUpload.server,
                    fileToUpload
                )
            }
            .flatMap { fileResponse ->
                filesDao.save(fileResponse.data).andThen(Single.just(fileResponse))
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { fileResponse -> uploadSuccess(fileResponse.data) },
                { e -> Log.d("tag", e.stackTraceToString()); uploadError(e) }
            )
            .dispatchTo(disposable)

    }

    companion object {
        const val FILE_URI_EXTRA = "file uri extra"
    }

    private fun notifySuccess(file: UploadFileData) {
        NotificationManagerCompat.from(this).notify(
            file.hashCode(),
            GoFileNotificationManager.buildFinishedUploadingNotification(this, file.fileName, file.directLink)
        )
    }

    private fun notifyError(id: Int) {
        NotificationManagerCompat.from(this).notify(
            id,
            GoFileNotificationManager.buildUploadingErrorNotification(this)
        )
    }

}
