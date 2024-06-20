package com.example.githubfinal.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.githubfinal.Repo.GithubRepository


class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val accountRepository = GithubRepository(application)

    fun searchUser(query: String) = accountRepository.searchUser(query)

}