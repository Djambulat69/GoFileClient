package com.djambulat69.gofileclient.utils

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.lifecycle.*
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable


fun ContentResolver.queryName(uri: Uri): String? {
    return query(uri, null, null, null, null)?.use { cursor ->
        val nameColumn = cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME)

        cursor.moveToFirst()

        return@use cursor.getString(nameColumn)
    }
}

fun ContentResolver.querySize(uri: Uri): Int? {
    return query(uri, null, null, null, null)?.use { cursor ->
        val sizeColumn = cursor.getColumnIndexOrThrow(OpenableColumns.SIZE)
        cursor.moveToFirst()
        return@use cursor.getInt(sizeColumn)
    }
}

fun Context.toast(text: CharSequence) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Disposable.dispatchTo(compositeDisposable: CompositeDisposable) {
    compositeDisposable.add(this)
}

fun CompositeDisposable.disposeSafe() {
    if (!this.isDisposed) {
        dispose()
    }
}

val RecyclerView.ViewHolder.context: Context get() = itemView.context

data class Progress(val current: Int, val max: Int)
