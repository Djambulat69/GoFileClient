package com.djambulat69.gofileclient.ui.uploadFile

import android.util.Log
import androidx.lifecycle.ViewModel
import com.djambulat69.gofileclient.network.FileToUpload
import com.djambulat69.gofileclient.network.GoFileApiServiceHelper
import com.djambulat69.gofileclient.network.UploadFileData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject

class UploadFileViewModel : ViewModel() {

    private val apiServiceHelper = GoFileApiServiceHelper()

    private val uploadFileDataSubject = BehaviorSubject.create<UploadFileData>()
    val uploadFileData: Observable<UploadFileData> = uploadFileDataSubject

    fun uploadFile(file: FileToUpload) {

        apiServiceHelper.getServer()
            .subscribeOn(Schedulers.io())
            .flatMap { getServerResponse ->
                apiServiceHelper.uploadFile(
                    getServerResponse.data.server,
                    file.bytes,
                    file.mimeType
                )
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response -> uploadFileDataSubject.onNext(response.data) },
                { e -> Log.d("tag", e.stackTraceToString()) }
            )

    }

}
