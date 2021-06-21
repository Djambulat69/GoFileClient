package com.djambulat69.gofileclient.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.djambulat69.gofileclient.GoFileApp
import com.djambulat69.gofileclient.network.UploadFileData

@Database(entities = [UploadFileData::class], exportSchema = false, version = 1)
abstract class FilesDatabase : RoomDatabase() {

    abstract fun filesDao(): FilesDao

    companion object {

        private const val DATABASE_NAME = "Files database"

        val instance: FilesDatabase by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            Room.databaseBuilder(
                GoFileApp.applicationContext(),
                FilesDatabase::class.java, DATABASE_NAME
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}
