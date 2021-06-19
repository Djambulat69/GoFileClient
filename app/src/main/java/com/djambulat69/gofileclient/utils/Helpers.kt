package com.djambulat69.gofileclient.utils

import android.content.ContentResolver
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.djambulat69.gofileclient.R
import com.google.android.material.snackbar.Snackbar

const val MIME_TYPE_ALL = "*/*"
const val MIME_TYPE_TEXT_ALL = "text/*"

fun Context.getAccountSharedPreferences(): SharedPreferences {
    return getSharedPreferences(
        getString(R.string.account_shared_preferences_name),
        Context.MODE_PRIVATE
    )
}

fun ContentResolver.queryName(uri: Uri): String? {
    return query(uri, null, null, null, null)?.use { cursor ->
        val nameColumn = cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME)

        cursor.moveToFirst()

        return@use cursor.getString(nameColumn)
    }
}

fun EditText.setActionListener(targetActionId: Int, callback: (String) -> Unit) {
    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == targetActionId) {
            callback(text.toString())
        }
        return@setOnEditorActionListener false
    }
}

fun Fragment.setChildFragmentResultListener(
    requestKey: String,
    listener: ((requestKey: String, bundle: Bundle) -> Unit)
) {
    childFragmentManager.setFragmentResultListener(requestKey, this, listener)
}

fun Context.toast(text: CharSequence) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun View.snackBar(text: CharSequence) {
    Snackbar.make(this, text, Snackbar.LENGTH_SHORT).show()
}
