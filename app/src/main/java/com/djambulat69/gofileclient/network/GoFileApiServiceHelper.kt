package com.djambulat69.gofileclient.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

class GoFileApiServiceHelper {

    private val retrofitService =
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(
                OkHttpClient().newBuilder()
                    .addInterceptor(TokenInterceptor)
                    .addInterceptor(
                        HttpLoggingInterceptor().apply {
                            level = HttpLoggingInterceptor.Level.BODY
                        }
                    )
                    .build()
            )
            .addConverterFactory(
                Json {
                    ignoreUnknownKeys = true
                }.asConverterFactory(("application/json").toMediaType())
            )
            .build()
            .create(GoFileApiService::class.java)


    fun getServer(): Single<GetServerResponse> = retrofitService.getServer()

    fun uploadFile(server: String, bytes: ByteArray, mimeType: String): Single<UploadFileResponse> {
        val url = BASE_URL.replaceFirst("api", server) + "uploadFile"

        val requestBody = bytes.toRequestBody(mimeType.toMediaType())

        val file = MultipartBody.Part.createFormData("name", "null", requestBody)

        return retrofitService.uploadFile(uploadFileUrl = url, file)
    }

    fun createFolder(parentFolderId: Int, folderName: String): Completable =
        retrofitService.createFolder(parentFolderId, folderName)

    fun setFolderOptions(
        folderId: Int,
        option: String,
        value: String
    ): Completable = retrofitService.setFolderOptions(folderId, option, value)

    fun deleteFolder(
        folderId: Int
    ): Completable = retrofitService.deleteFolder(folderId)

    fun deleteFile(
        fileId: Int
    ): Completable = retrofitService.deleteFile(fileId)

    fun getAccountDetails(): Single<GetAccountDetailsResponse> =
        retrofitService.getAccountDetails()

    companion object {
        private const val BASE_URL = "https://api.gofile.io/"
    }
}
