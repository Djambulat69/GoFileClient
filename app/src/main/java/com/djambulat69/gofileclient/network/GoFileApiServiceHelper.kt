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


    private val json: Json = Json { ignoreUnknownKeys = true }

    private val retrofitService =
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(
                json.asConverterFactory(("application/json").toMediaType())
            )
            .build()
            .create(GoFileApiService::class.java)


    fun uploadFile(
        fileToUpload: FileToUpload,
        progressSub: PublishSubject<Progress>
    ): Single<UploadFileResponse> {

        val requestBody = InputStreamRequestBody(
            fileToUpload.mimeType.toMediaType(),
            fileToUpload.inputStream,
            fileToUpload.size.toLong(),
            progressSub
        )

        val file = MultipartBody.Part.createFormData("file", fileToUpload.fileName, requestBody)

        return getServer().flatMap { serverResponse ->
            val fileUrl = constructUploadFileUrl(serverResponse.data.server)
            retrofitService.uploadFile(fileUrl, file)
        }
    }

    private fun getServer(): Single<GetServerResponse> = retrofitService.getServer()

    private fun constructUploadFileUrl(server: String): String {
        return BASE_URL.replaceFirst("api", server) + "uploadFile"
    }
}
