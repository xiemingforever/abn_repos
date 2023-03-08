package com.apprecipe.abngit.data.api

import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubApi {

    @GET("repos")
    suspend fun getGitRepos(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): List<RepoNetwork>
}
