package com.djambulat69.gofileclient.network

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

    @PUT("createFolder")
    fun createFolder(
        @Query("parentFolderId") parentFolderId: Int,
        @Query("folderName") folderName: String,
    ): Completable

    @PUT("setFolderOptions")
    fun setFolderOptions(
        @Query("folderId") folderId: Int,
        @Query("option") option: String,
        @Query("value") value: String
    ): Completable

    @DELETE("deleteFolder")
    fun deleteFolder(
        @Query("folderId") folderId: Int
    ): Completable

    @DELETE("deleteFile")
    fun deleteFile(
        @Query("fileId") fileId: Int
    ): Completable

    @GET("getAccountDetails")
    fun getAccountDetails(
        @Query("allDetails") allDetails: Boolean = true
    ): Single<GetAccountDetailsResponse>

}
