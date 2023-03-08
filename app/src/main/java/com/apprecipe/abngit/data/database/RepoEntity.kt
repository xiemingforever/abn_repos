package com.apprecipe.abngit.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repos")
data class RepoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String,
    @ColumnInfo(name = "full_name") val fullName: String,
    val description: String?,
    @ColumnInfo(name = "avatar_url") val avatarUrl: String?,
    val visibility: String,
    val isPrivate: Boolean,
    @ColumnInfo(name = "html_url") val htmlUrl: String,
    val page: Long
)
