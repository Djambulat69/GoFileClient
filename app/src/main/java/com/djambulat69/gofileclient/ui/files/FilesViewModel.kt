package com.djambulat69.gofileclient.ui.files

import android.util.Log
import androidx.lifecycle.ViewModel
import com.djambulat69.gofileclient.db.FilesDatabase
import com.djambulat69.gofileclient.network.UploadFileData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject

class FilesViewModel : ViewModel() {

    private val disposable = CompositeDisposable()

    private val filesDao = FilesDatabase.instance.filesDao()

    private val filesSubject = BehaviorSubject.create<List<UploadFileData>>()
    val files: Observable<List<UploadFileData>> = filesSubject

    init {
        loadFiles()
    }

    private fun loadFiles() {
        disposable.add(
            filesDao.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { files -> filesSubject.onNext(files) },
                    { e -> Log.d("tag", e.stackTraceToString()) }
                )
        )
    }

}
