package com.apprecipe.abngit.data.api

import com.apprecipe.abngit.data.model.Repo
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RepoNetwork(
    @Json(name = "id") val repoId: Long,
    val name: String,
    @Json(name = "full_name") val fullName: String,
    val description: String?,
    val owner: Owner,
    val visibility: RepoVisibility?,
    @Json(name = "private") val isPrivate: Boolean,
    @Json(name = "html_url") val htmlUrl: String
)

@JsonClass(generateAdapter = true)
data class Owner(
    @Json(name = "avatar_url") val avatarUrl: String?
)

enum class RepoVisibility(val string: String) {
    @Json(name = "public")
    PUBLIC("public"),

    @Json(name = "private")
    PRIVATE("private"),
}

fun RepoNetwork.toRepoEntity(): Repo = Repo(
    repoId = this.repoId,
    name = this.name,
    fullName = this.fullName,
    description = this.description,
    avatarUrl = this.owner.avatarUrl,
    visibility = this.visibility?.string ?: "",
    isPrivate = this.isPrivate,
    htmlUrl = this.htmlUrl,
)
