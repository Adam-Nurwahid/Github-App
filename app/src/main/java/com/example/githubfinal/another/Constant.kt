package com.example.githubfinal.another

import androidx.datastore.preferences.core.booleanPreferencesKey
import com.example.githubfinal.R

object Constant {
    const val EXTRA_ACCOUNT = "USER"
    const val EXTRA_USERNAME = "USERNAME"
    const val USER_DATASTORE = "USER_DATASTORE"
    val THEME_KEY = booleanPreferencesKey("THEME_SETTING")
    val TAB_TITLES = intArrayOf(
        R.string.following,
        R.string.followers
    )
}