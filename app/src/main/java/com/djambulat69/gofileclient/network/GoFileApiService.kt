package com.djambulat69.gofileclient.network

import io.reactivex.rxjava3.core.Single
import okhttp3.MultipartBody
import retrofit2.http.*

interface GoFileApiService {

    @GET("getServer")
    fun getServer(): Single<GetServerResponse>

    @Multipart
    @POST
    fun uploadFile(
        @Url uploadFileUrl: String,
        @Part file: MultipartBody.Part
    ): Single<UploadFileResponse>

}
