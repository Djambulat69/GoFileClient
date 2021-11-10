package com.djambulat69.gofileclient.network

import java.io.InputStream

class FileToUpload(
    val fileName: String,
    val inputStream: InputStream?,
    val mimeType: String,
    val server: String,
    val size: Int
)
