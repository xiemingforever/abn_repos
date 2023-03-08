package com.apprecipe.abngit.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.apprecipe.abngit.data.model.Repo

@Dao
interface RepoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repos: List<Repo>)

    @Query("SELECT * FROM repos WHERE repoId = :repoId")
    suspend fun findRepoById(repoId: Long): Repo

    @Query("SELECT * FROM repos")
    fun pagingSource(): PagingSource<Int, Repo>

    @Query("DELETE FROM repos")
    suspend fun clearAll()
}
