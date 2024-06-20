package com.example.githubfinal.database.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface  GithubDao{


    @Delete

    suspend fun deleteFromFavorite(user: AppEntity)

    @Query("SELECT * FROM account WHERE username = :username")
    suspend fun isFavorite(username: String): AppEntity?

    @Query("SELECT * FROM account ORDER BY username ASC")
    suspend fun getFavorites(): List<AppEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorite(user: AppEntity)
}