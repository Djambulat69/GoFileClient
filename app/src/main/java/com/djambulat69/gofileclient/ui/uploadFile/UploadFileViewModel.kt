package com.djambulat69.gofileclient.ui.uploadFile

import android.util.Log
import androidx.lifecycle.ViewModel
import com.djambulat69.gofileclient.db.FilesDao
import com.djambulat69.gofileclient.db.FilesDatabase
import com.djambulat69.gofileclient.network.FileToUpload
import com.djambulat69.gofileclient.network.GoFileApiServiceHelper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject

class UploadFileViewModel : ViewModel() {

    private val apiServiceHelper = GoFileApiServiceHelper
    private val filesDao: FilesDao = FilesDatabase.instance.filesDao()

    private val uploadFileDataSubject = BehaviorSubject.create<String>()
    val uploadedFiles: Observable<String> = uploadFileDataSubject

    fun uploadFile(file: FileToUpload) {

        apiServiceHelper.getServer()
            .subscribeOn(Schedulers.io())
            .flatMap { getServerResponse ->
                apiServiceHelper.uploadFile(
                    getServerResponse.data.server,
                    file
                )
            }
            .flatMapCompletable { fileResponse ->
                filesDao.save(fileResponse.data)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { uploadFileDataSubject.onNext(file.fileName) },
                { e -> Log.d("tag", e.stackTraceToString()) }
            )

    }

}
