package com.example.githubfinal.database.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [AppEntity::class], exportSchema = false, version = 1)
abstract class GithubDatabase : RoomDatabase() {
    abstract fun accountDao(): GithubDao

    companion object {
        @Volatile
        private var INSTANCE: GithubDatabase? = null

        @JvmStatic
        fun getInstance(context: Context): GithubDatabase {
            if (INSTANCE == null) {
                synchronized(GithubDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        GithubDatabase::class.java,
                        "User.db"
                    ).build()
                }
            }
            return INSTANCE as GithubDatabase
        }
    }
}