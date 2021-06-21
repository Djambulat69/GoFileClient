package com.djambulat69.gofileclient.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.core.Single
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

object GoFileApiServiceHelper {

    private const val BASE_URL = "https://api.gofile.io/"


    private val retrofitService =
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(
                OkHttpClient().newBuilder()
                    .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
                    .build()
            )
            .addConverterFactory(
                Json { ignoreUnknownKeys = true }.asConverterFactory(("application/json").toMediaType())
            )
            .build()
            .create(GoFileApiService::class.java)


    fun getServer(): Single<GetServerResponse> = retrofitService.getServer()

    fun uploadFile(
        server: String,
        fileToUpload: FileToUpload,
    ): Single<UploadFileResponse> {
        val url = BASE_URL.replaceFirst("api", server) + "uploadFile"

        val requestBody = fileToUpload.bytes.toRequestBody(fileToUpload.mimeType.toMediaType())

        val file = MultipartBody.Part.createFormData("file", fileToUpload.fileName, requestBody)

        return retrofitService.uploadFile(uploadFileUrl = url, file)
    }
}
