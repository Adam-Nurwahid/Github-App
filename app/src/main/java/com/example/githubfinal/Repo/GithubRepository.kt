package com.example.githubfinal.Repo

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.githubfinal.another.SettingPrefrences
import com.example.githubfinal.database.ApiConfig
import com.example.githubfinal.database.ApiService
import com.example.githubfinal.database.SearchResponse
import com.example.githubfinal.database.room.AppEntity
import com.example.githubfinal.database.room.GithubDao
import com.example.githubfinal.database.room.GithubDatabase


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GithubRepository(application: Application) {
    private val apiService: ApiService = ApiConfig.create()
    private val accountDao: GithubDao
    private val settingPref: SettingPrefrences

    init {
        val database: GithubDatabase = GithubDatabase.getInstance(application)
        accountDao = database.accountDao()
        settingPref = SettingPrefrences.getInstance(application)
    }

    fun searchUser(query: String): LiveData<com.example.githubfinal.another.Result<List<AppEntity>>> {
        val listUser = MutableLiveData<com.example.githubfinal.another.Result<List<AppEntity>>>()
        listUser.postValue(com.example.githubfinal.another.Result.Loading())

        apiService.searchUsers(query, "50").enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>,
            ) {
                val list = response.body()?.items

                if (list.isNullOrEmpty()) {
                    listUser.postValue(com.example.githubfinal.another.Result.Error(null))
                } else {
                    listUser.postValue(com.example.githubfinal.another.Result.Success(list))
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                listUser.postValue(com.example.githubfinal.another.Result.Error(t.message))
            }
        })

        return listUser
    }

    suspend fun getUser(username: String): LiveData<com.example.githubfinal.another.Result<AppEntity>> {
        val user = MutableLiveData<com.example.githubfinal.another.Result<AppEntity>>()

        if (accountDao.isFavorite(username) != null) {
            user.postValue(com.example.githubfinal.another.Result.Success(accountDao.isFavorite(username)))
        } else {
            apiService.getUser(username).enqueue(object : Callback<AppEntity> {
                override fun onResponse(
                    call: Call<AppEntity>,
                    response: Response<AppEntity>,
                ) {
                    val result = response.body()
                    user.postValue(com.example.githubfinal.another.Result.Success(result))
                }

                override fun onFailure(call: Call<AppEntity>, t: Throwable) {
                    user.postValue(com.example.githubfinal.another.Result.Error(t.message))
                }
            })
        }

        return user
    }

    fun getFollowers(username: String): LiveData<com.example.githubfinal.another.Result<List<AppEntity>>> {
        val listUser = MutableLiveData<com.example.githubfinal.another.Result<List<AppEntity>>>()
        listUser.postValue(com.example.githubfinal.another.Result.Loading())

        apiService.getFollowers(username).enqueue(object : Callback<List<AppEntity>> {
            override fun onResponse(
                call: Call<List<AppEntity>>,
                response: Response<List<AppEntity>>,
            ) {
                val list = response.body()

                if (list.isNullOrEmpty()) {
                    listUser.postValue(com.example.githubfinal.another.Result.Error(null))
                } else {
                    listUser.postValue(com.example.githubfinal.another.Result.Success(list))
                }
            }

            override fun onFailure(call: Call<List<AppEntity>>, t: Throwable) {
                listUser.postValue(com.example.githubfinal.another.Result.Error(t.message))
            }
        })

        return listUser
    }

    fun getFollowing(username: String): LiveData<com.example.githubfinal.another.Result<List<AppEntity>>> {
        val listUser = MutableLiveData<com.example.githubfinal.another.Result<List<AppEntity>>>()
        listUser.postValue(com.example.githubfinal.another.Result.Loading())

        apiService.getFollowing(username).enqueue(object : Callback<List<AppEntity>> {
            override fun onResponse(
                call: Call<List<AppEntity>>,
                response: Response<List<AppEntity>>,
            ) {
                val list = response.body()

                if (list.isNullOrEmpty()) {
                    listUser.postValue(com.example.githubfinal.another.Result.Error(null))
                } else {
                    listUser.postValue(com.example.githubfinal.another.Result.Success(list))
                }
            }

            override fun onFailure(call: Call<List<AppEntity>>, t: Throwable) {
                listUser.postValue(com.example.githubfinal.another.Result.Error(t.message))
            }
        })

        return listUser
    }

    suspend fun getFavorites(): LiveData<com.example.githubfinal.another.Result<List<AppEntity>>> {
        val listFavorite = MutableLiveData<com.example.githubfinal.another.Result<List<AppEntity>>>()
        listFavorite.postValue(com.example.githubfinal.another.Result.Loading())

        if (accountDao.getFavorites().isEmpty()) {
            listFavorite.postValue(com.example.githubfinal.another.Result.Error(null))
        } else {
            listFavorite.postValue(com.example.githubfinal.another.Result.Success(accountDao.getFavorites()))
        }

        return listFavorite
    }

    suspend fun addToFavorite(user: AppEntity) = accountDao.addToFavorite(user)

    suspend fun deleteFromFavorite(user: AppEntity) = accountDao.deleteFromFavorite(user)

    suspend fun setTheme(isDarkMode: Boolean) = settingPref.setTheme(isDarkMode)

    fun getTheme() = settingPref.getTheme()
}
