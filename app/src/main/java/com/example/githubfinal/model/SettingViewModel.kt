package com.example.githubfinal.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.githubfinal.Repo.GithubRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingViewModel(application: Application) : AndroidViewModel(application) {
    private val userRepository = GithubRepository(application)

    fun setTheme(isDarkMode: Boolean) = viewModelScope.launch {
        userRepository.setTheme(isDarkMode)
    }

    fun getTheme() = userRepository.getTheme().asLiveData(Dispatchers.IO)
}