package com.apprecipe.abngit.data.database

import androidx.paging.PagingSource
import androidx.room.*

@Dao
interface RepoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repos: List<RepoEntity>)

    @Query("SELECT * FROM repos")
    fun loadAllRepos(): List<RepoEntity>

    @Query("SELECT * FROM repos WHERE id = :id")
    suspend fun findRepoById(id: Int): RepoEntity

    @Query("SELECT * FROM repos")
    fun pagingSource(): PagingSource<Int, RepoEntity>

    @Query("DELETE FROM repos")
    suspend fun clearAll()
}

@Database(entities = [RepoEntity::class], version = 1)
abstract class AbnRepoDb : RoomDatabase() {
    abstract fun repoDao(): RepoDao
}
