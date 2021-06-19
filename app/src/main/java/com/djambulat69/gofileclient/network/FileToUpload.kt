package com.djambulat69.gofileclient.network

import android.net.Uri

class FileToUpload(
    val fileUri: Uri,
    val fileName: String,
    val folderId: Int,
    val description: String,
    val password: String,
    val tags: List<String>,
    val server: String? = null,
    val bytes: ByteArray,
    val mimeType: String
)
