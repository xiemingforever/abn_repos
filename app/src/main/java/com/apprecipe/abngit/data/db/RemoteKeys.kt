package com.apprecipe.abngit.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey val repoId: Long,
    val prevKey: Int?,
    val nextKey: Int?,
    @ColumnInfo(name = "created_at") val createdAt: Long = System.currentTimeMillis()
)
