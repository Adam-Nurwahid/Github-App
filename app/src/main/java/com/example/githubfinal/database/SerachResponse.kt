package com.example.githubfinal.database

import com.example.githubfinal.database.room.AppEntity
import com.squareup.moshi.Json

data class SearchResponse(
    @field:Json(name = "items") val items: List<AppEntity>
)