package com.example.githubfinal.database

import com.example.githubfinal.database.room.AppEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun searchUsers(
        @Query("q") query: String,
        @Query("per_page") per_page: String,
    ): Call<SearchResponse>

    @GET("users/{username}")
    fun getUser(@Path("username") username: String
    ): Call<AppEntity>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String
    ): Call<List<AppEntity>>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String
    ): Call<List<AppEntity>>
}
