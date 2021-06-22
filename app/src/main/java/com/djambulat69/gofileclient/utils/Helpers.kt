package com.djambulat69.gofileclient.utils

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.view.View
import android.widget.Toast
import androidx.lifecycle.*
import com.google.android.material.snackbar.Snackbar
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

const val MIME_TYPE_ALL = "*/*"

const val UPLOAD_FILE_SIZE_LIMIT_BYTES = 26_214_400

fun ContentResolver.queryName(uri: Uri): String? {
    return query(uri, null, null, null, null)?.use { cursor ->
        val nameColumn = cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME)

        cursor.moveToFirst()

        return@use cursor.getString(nameColumn)
    }
}

fun ContentResolver.querySize(uri: Uri): Int {
    return query(uri, null, null, null, null)?.use { cursor ->
        val sizeColumn = cursor.getColumnIndexOrThrow(OpenableColumns.SIZE)

        cursor.moveToFirst()

        return@use cursor.getInt(sizeColumn)
    } ?: 0
}

fun Context.toast(text: CharSequence) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun View.snackBar(text: CharSequence) {
    Snackbar.make(this, text, Snackbar.LENGTH_SHORT).show()
}

fun Disposable.dispatchTo(compositeDisposable: CompositeDisposable) {
    compositeDisposable.add(this)
}

fun CompositeDisposable.disposeSafe() {
    if (!this.isDisposed) {
        dispose()
    }
}
