package com.djambulat69.gofileclient.network

import okhttp3.MediaType
import okhttp3.RequestBody
import okio.BufferedSink
import okio.source
import java.io.InputStream

class InputStreamRequestBody(
    private val contentType: MediaType,
    private val stream: InputStream?
) : RequestBody() {

    override fun contentLength(): Long = -1L

    override fun contentType(): MediaType = contentType

    override fun writeTo(sink: BufferedSink) {
        stream?.use {
            sink.writeAll(it.source())
        }
    }

}
