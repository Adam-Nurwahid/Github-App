package com.example.githubfinal.database.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "account")
data class AppEntity(
    @field:Json(name = "id") @PrimaryKey val id: Int? = 0,

    @field:Json(name = "login") val username: String? = "",

    @field:Json(name = "name") val name: String? = "",

    @field:Json(name = "followers") val follower: Int? = 0,

    @field:Json(name = "following") val following: Int? = 0,

    @field:Json(name = "avatar_url") val avatar: String? = "",

    var isFavorite: Boolean? = false,
)




