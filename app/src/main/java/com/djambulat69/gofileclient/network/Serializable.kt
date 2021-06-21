package com.djambulat69.gofileclient.network

import androidx.room.Entity
import androidx.room.PrimaryKey
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

@Entity(tableName = "downloaded_files_table")
@Serializable
data class UploadFileData(
    @SerialName("downloadPage") val downloadPage: String,
    @SerialName("fileId") @PrimaryKey val fileId: String,
    @SerialName("fileName") val fileName: String,
    @SerialName("directLink") val directLink: String
)
