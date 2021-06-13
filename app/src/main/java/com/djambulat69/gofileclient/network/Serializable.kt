package com.djambulat69.gofileclient.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class GetServerResponse(
    @SerialName("status") val status: String,
    @SerialName("data") val data: Server
)

@Serializable
class Server(
    @SerialName("server") val server: String
)

@Serializable
class UploadFileResponse(
    @SerialName("status") val status: String,
    @SerialName("data") val data: UploadFileData
)

@Serializable
class UploadFileData(
    @SerialName("downloadPage") val downloadPage: String,
    @SerialName("code") val code: String,
    @SerialName("parentFolder") val parentFolder: String,
    @SerialName("fileId") val fileId: String,
    @SerialName("fileName") val fileName: String,
    @SerialName("md5") val md5: String,
    @SerialName("directLink") val directLink: String,
    @SerialName("info") val info: String
)

@Serializable
class GetAccountDetailsResponse(
    @SerialName("status") val status: String,
    @SerialName("data") val accountDetails: AccountDetails
)

@Serializable
class AccountDetails(
    @SerialName("token") val token: String,
    @SerialName("email") val email: String,
    @SerialName("tier") val tier: String,
    @SerialName("rootFolder") val rootFolder: String,
    @SerialName("foldersCount") val foldersCount: Int,
    @SerialName("filesCount") val filesCount: Int,
    @SerialName("totalSize") val totalSize: Int,
    @SerialName("totalDownloadCount") val totalDownloadCount: Int
)
