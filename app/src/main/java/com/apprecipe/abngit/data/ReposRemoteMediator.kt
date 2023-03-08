package com.apprecipe.abngit.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.apprecipe.abngit.data.api.GitHubApi
import com.apprecipe.abngit.data.api.toRepoEntity
import com.apprecipe.abngit.data.db.AbnRepoDb
import com.apprecipe.abngit.data.db.RemoteKeys
import com.apprecipe.abngit.data.model.Repo
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit

private const val STARTING_PAGE_INDEX = 1

@OptIn(ExperimentalPagingApi::class)
class ReposRemoteMediator(
    private val db: AbnRepoDb,
    private val api: GitHubApi
) : RemoteMediator<Int, Repo>() {

    override suspend fun initialize(): InitializeAction {
        // Refresh if the cached data is older than one hour
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)
        return if (System.currentTimeMillis() - (db.remoteKeysDao().getCreationTime()
                ?: 0) < cacheTimeout
        ) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Repo>
    ): MediatorResult {

        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                if (prevKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                if (nextKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
                nextKey
            }
        }

        return try {
            val repos = api.getGitRepos(page, state.config.pageSize).map { it.toRepoEntity() }
            val endOfPaginationReached = repos.size < state.config.pageSize

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.remoteKeysDao().clearAll()
                    db.repoDao().clearAll()
                }
                val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = repos.map {
                    RemoteKeys(repoId = it.repoId, prevKey = prevKey, nextKey = nextKey)
                }
                db.remoteKeysDao().insertAll(keys)
                db.repoDao().insertAll(repos)
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Repo>
    ): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.repoId?.let { repoId ->
                db.remoteKeysDao().remoteKeysRepoId(repoId)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Repo>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { repo ->
                db.remoteKeysDao().remoteKeysRepoId(repo.repoId)
            }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Repo>): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { repo ->
                db.remoteKeysDao().remoteKeysRepoId(repo.repoId)
            }
    }
}
