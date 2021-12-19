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
import com.djambulat69.gofileclient.ui.GoFileNotificationManager
import com.djambulat69.gofileclient.utils.*
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.internal.functions.Functions
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject

class UploadFileService : Service() {

    private val disposable = CompositeDisposable()

    private val apiServiceHelper = GoFileApiServiceHelper
    private val filesDao = FilesDatabase.instance.filesDao()

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {

        val fileUri = intent.extras?.getParcelable<Uri>(FILE_URI_EXTRA) ?: return START_NOT_STICKY
        val fileName = contentResolver.queryName(fileUri).orEmpty()

        startForeground(
            startId,
            GoFileNotificationManager.buildUploadingFileNotification(applicationContext, fileName)
        )

        val progressSubject = PublishSubject.create<Progress>()
        uploadFile(fileUri, progressSubject)

        listenUploadProgress(progressSubject, startId, fileName)

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        disposable.disposeSafe()
        super.onDestroy()
    }

    private fun uploadFile(uri: Uri, progressSubject: PublishSubject<Progress>) {
        Single.fromCallable {
            with(contentResolver) {
                FileToUpload(
                    fileName = queryName(uri).orEmpty(),
                    inputStream = openInputStream(uri),
                    mimeType = getType(uri).orEmpty(),
                    size = querySize(uri) ?: NO_SIZE
                )
            }
        }
            .subscribeOn(Schedulers.io())
            .flatMap { fileToUpload ->
                apiServiceHelper.uploadFile(fileToUpload, progressSubject)
            }
            .flatMapCompletable { fileResponse ->
                filesDao.save(fileResponse.data)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally {
                progressSubject.onComplete()
                stopForeground(true)
                stopSelf()
            }
            .subscribe(
                Functions.EMPTY_ACTION,
                { e -> Log.d("tag", e.stackTraceToString()) }
            )
            .dispatchTo(disposable)
    }

    private fun listenUploadProgress(progressObservable: Observable<Progress>, id: Int, fileName: String) {
        progressObservable
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { progress ->
                NotificationManagerCompat.from(applicationContext).notify(
                    id, GoFileNotificationManager.buildUploadingFileNotification(applicationContext, fileName, progress)
                )
            }
            .dispatchTo(disposable)
    }

    companion object {
        const val FILE_URI_EXTRA = "file uri extra"
        const val NO_SIZE = -1
    }
}
