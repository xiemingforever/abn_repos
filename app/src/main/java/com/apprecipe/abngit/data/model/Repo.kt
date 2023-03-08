package com.apprecipe.abngit.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repos")
data class Repo(
    @PrimaryKey(autoGenerate = true)
    val generatedId: Long = 0L,
    val repoId: Long,
    val name: String,
    @ColumnInfo(name = "full_name") val fullName: String,
    val description: String?,
    @ColumnInfo(name = "avatar_url") val avatarUrl: String?,
    val visibility: String,
    val isPrivate: Boolean,
    @ColumnInfo(name = "html_url") val htmlUrl: String,
)
