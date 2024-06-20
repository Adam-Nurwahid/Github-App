package com.example.githubfinal.another

import android.content.Context
import android.annotation.SuppressLint
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.githubfinal.another.Constant.THEME_KEY
import com.example.githubfinal.another.Constant.USER_DATASTORE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingPrefrences (private val context: Context){
    private val Context.userPreferenceDataStore: DataStore<Preferences> by preferencesDataStore(
        name = USER_DATASTORE
    )

    suspend fun setTheme(isDarkModeActive: Boolean) {
        context.userPreferenceDataStore.edit {
            it[THEME_KEY] = isDarkModeActive
        }
    }

    fun getTheme(): Flow<Boolean> =
        context.userPreferenceDataStore.data.map {
            it[THEME_KEY] ?: false
        }

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var instance: SettingPrefrences? = null

        fun getInstance(context: Context): SettingPrefrences =
            instance ?: synchronized(this) {
                val newInstance = instance ?: SettingPrefrences(context).also { instance = it }
                newInstance
            }
    }


}