package com.djambulat69.gofileclient.network

import com.djambulat69.gofileclient.utils.Progress
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import retrofit2.Retrofit

object GoFileApiServiceHelper {

    private const val BASE_URL = "https://api.gofile.io/"


    private val retrofitService =
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(
                Json { ignoreUnknownKeys = true }.asConverterFactory(("application/json").toMediaType())
            )
            .build()
            .create(GoFileApiService::class.java)


    fun getServer(): Single<GetServerResponse> = retrofitService.getServer()

    fun uploadFile(
        server: String,
        fileToUpload: FileToUpload,
        progressSub: PublishSubject<Progress>
    ): Single<UploadFileResponse> {
        val url = constructUploadFileUrl(server)

        val requestBody = InputStreamRequestBody(
            fileToUpload.mimeType.toMediaType(),
            fileToUpload.inputStream,
            fileToUpload.size.toLong(),
            progressSub
        )

        val file = MultipartBody.Part.createFormData("file", fileToUpload.fileName, requestBody)

        return retrofitService.uploadFile(uploadFileUrl = url, file)
    }

    private fun constructUploadFileUrl(server: String): String {
        return BASE_URL.replaceFirst("api", server) + "uploadFile"
    }
}
