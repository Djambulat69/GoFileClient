package com.djambulat69.gofileclient.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.djambulat69.gofileclient.network.UploadFileData
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable


@Dao
interface FilesDao {

    @Query("SELECT * FROM downloaded_files_table")
    fun getAll(): Flowable<List<UploadFileData>>

    @Insert
    fun save(filesData: UploadFileData): Completable

    @Delete
    fun delete(fileData: UploadFileData): Completable

}
