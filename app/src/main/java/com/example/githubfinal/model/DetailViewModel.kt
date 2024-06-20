package com.example.githubfinal.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubfinal.Repo.GithubRepository
import com.example.githubfinal.database.room.AppEntity
import kotlinx.coroutines.launch

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    private val userRepository = GithubRepository(application)

    suspend fun getUser(username: String) = userRepository.getUser(username)

    fun addToFavorite(user: AppEntity) = viewModelScope.launch {
        userRepository.addToFavorite(user)
    }

    fun deleteFromFavorite(user: AppEntity) = viewModelScope.launch {
        userRepository.deleteFromFavorite(user)
    }

    fun getFollowers(username: String) = userRepository.getFollowers(username)

    fun getFollowing(username: String) = userRepository.getFollowing(username)
}