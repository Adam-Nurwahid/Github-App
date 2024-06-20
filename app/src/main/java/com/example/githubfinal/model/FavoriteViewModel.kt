package com.example.githubfinal.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.githubfinal.Repo.GithubRepository

class FavoriteViewModel (application: Application) : AndroidViewModel(application) {
    private val userRepository = GithubRepository(application)

    suspend fun getFavorites() = userRepository.getFavorites()
}