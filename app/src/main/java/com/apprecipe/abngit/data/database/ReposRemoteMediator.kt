package com.apprecipe.abngit.data.database

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.apprecipe.abngit.data.network.GitHubApi
import com.apprecipe.abngit.data.network.NETWORK_PAGE_SIZE
import com.apprecipe.abngit.data.network.toRepoEntity
import retrofit2.HttpException
import java.io.IOException

private const val FIRST_PAGE_FROM_NETWORK = 1L

@OptIn(ExperimentalPagingApi::class)
class ReposRemoteMediator(
    private val db: AbnRepoDb,
    private val api: GitHubApi
) : RemoteMediator<Int, RepoEntity>() {

    private val repoDao: RepoDao = db.repoDao()

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RepoEntity>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> FIRST_PAGE_FROM_NETWORK
                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    }

                    lastItem.page + 1
                }
            }

            val response = api.getGitRepos(page).map { it.toRepoEntity(page) }

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    repoDao.clearAll()
                }

                repoDao.insertAll(response)
            }

            MediatorResult.Success(
                endOfPaginationReached = response.size < NETWORK_PAGE_SIZE
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}
