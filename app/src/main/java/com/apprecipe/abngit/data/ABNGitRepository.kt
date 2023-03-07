package com.apprecipe.abngit.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.apprecipe.abngit.data.database.AbnRepoDb
import com.apprecipe.abngit.data.database.RepoEntity
import com.apprecipe.abngit.data.database.ReposRemoteMediator
import com.apprecipe.abngit.data.network.GitHubApi
import com.apprecipe.abngit.data.network.NETWORK_PAGE_SIZE
import com.apprecipe.abngit.data.network.toRepoEntity
import javax.inject.Inject

class ABNGitRepository @Inject constructor(
    private val database: AbnRepoDb,
    private val api: GitHubApi
) {
    suspend fun getABNGitRepos(page: Long): List<RepoEntity> =
        api.getGitRepos(page).map { it.toRepoEntity(page) }

//    @OptIn(ExperimentalPagingApi::class)
//    fun reposOfABN() = Pager(
//        config = PagingConfig(NETWORK_PAGE_SIZE),
//        remoteMediator = ReposRemoteMediator(db = database, api = api)
//    ) {
//        database.repoDao().loadAllRepos()
//    }.flow
}
