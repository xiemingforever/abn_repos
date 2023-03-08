package com.apprecipe.abngit.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.apprecipe.abngit.data.model.Repo

@Database(
    entities = [Repo::class, RemoteKeys::class],
    version = 1
)
abstract class AbnRepoDb : RoomDatabase() {
    abstract fun repoDao(): RepoDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}
