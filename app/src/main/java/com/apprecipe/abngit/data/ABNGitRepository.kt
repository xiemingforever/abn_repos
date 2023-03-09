package com.apprecipe.abngit.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.apprecipe.abngit.data.api.GitHubApi
import com.apprecipe.abngit.data.db.AbnRepoDb
import com.apprecipe.abngit.data.model.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val NETWORK_PAGE_SIZE = 15

class ABNGitRepository @Inject constructor(
    private val database: AbnRepoDb,
    private val api: GitHubApi
) {

    fun getReposStream(): Flow<PagingData<Repo>> {
        val pagingSourceFactory = { database.repoDao().pagingSource() }

        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                initialLoadSize = NETWORK_PAGE_SIZE,
                // A value of 0 is not recommended, for demo purpose only.
                prefetchDistance = 0
            ),
            remoteMediator = ReposRemoteMediator(db = database, api = api),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    suspend fun getRepo(repoId: Long): Repo = database.repoDao().findRepoById(repoId)
}
