package com.djambulat69.gofileclient.network

import com.djambulat69.gofileclient.utils.Progress
import io.reactivex.rxjava3.subjects.PublishSubject
import okhttp3.MediaType
import okhttp3.RequestBody
import okio.BufferedSink
import okio.source
import java.io.InputStream

class InputStreamRequestBody(
    private val contentType: MediaType,
    private val stream: InputStream?,
    private val contentLength: Long = -1L,
    private val progressSub: PublishSubject<Progress>
) : RequestBody() {

    override fun contentLength(): Long = contentLength

    override fun contentType(): MediaType = contentType

    override fun writeTo(sink: BufferedSink) {
/*        stream?.use {
            sink.writeAll(it.source())
        }*/

        stream?.use {
            val source = it.source()
            var total: Long = 0
            var read: Long
            var completion = 0L

            do {
                read = source.read(sink.buffer, 2048L)
                total += read

                val newCompletion = ((total.toDouble() / contentLength.toDouble() ) * 100).toLong()
                if (newCompletion != completion) {
                    completion = newCompletion
                    progressSub.onNext(Progress(completion.toInt(), 100))
                }

                sink.flush()


            } while (read != -1L)

            progressSub.onComplete()
        }

    }

}
