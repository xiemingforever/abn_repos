package com.apprecipe.abngit.data.network

import retrofit2.http.GET
import retrofit2.http.Query

const val NETWORK_PAGE_SIZE = 10

interface GitHubApi {

    @GET("repos")
    suspend fun getGitRepos(
        @Query("page") page: Long,
        @Query("per_page") perPage: Int = NETWORK_PAGE_SIZE,
    ): List<RepoNetwork>
}
