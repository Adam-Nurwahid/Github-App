package com.example.githubfinal.Activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubfinal.Adapter.MainAdapter
import com.example.githubfinal.R
import com.example.githubfinal.another.Conditions
import com.example.githubfinal.database.room.AppEntity
import com.example.githubfinal.databinding.ActivityMainBinding
import com.example.githubfinal.model.MainViewModel
import com.example.githubfinal.model.SettingViewModel

class MainActivity : AppCompatActivity(), Conditions<List<AppEntity>> {
    private lateinit var binding: ActivityMainBinding
    private lateinit var userAdapter: MainAdapter
    private val viewModel by viewModels<MainViewModel>()
    private val settingViewModel by viewModels<SettingViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Thread.sleep(2000)
        userAdapter = MainAdapter()
        binding.initSearch.rvListUser.apply {
            adapter = userAdapter
            layoutManager =
                LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        }

        viewModel.searchUser(query = "a").observe(this@MainActivity) { a ->
            when (a) {
                is  com.example.githubfinal.another.Result.Loading -> onLoading()
                is  com.example.githubfinal.another.Result.Success -> a.data?.let { b -> onSuccess(b) }
                is  com.example.githubfinal.another.Result.Error -> onFailed(a.message)
                else -> {}
            }
        }
        settingViewModel.getTheme().observe(this@MainActivity) { isDarkMode ->
            if (isDarkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
        with(binding) {
            binding.initSearch.searchView.setupWithSearchBar(initSearch.searchBar)
            binding.initSearch.searchView.editText.setOnEditorActionListener { textView, i, keyEvent ->
                val search = binding.initSearch.searchView.text.toString()
                initSearch.searchView.hide()
                viewModel.searchUser(search).observe(this@MainActivity) { a ->
                    when (a) {
                        is  com.example.githubfinal.another.Result.Loading -> onLoading()
                        is  com.example.githubfinal.another.Result.Success -> a.data?.let { b ->
                            onSuccess(
                                b
                            )
                        }

                        is  com.example.githubfinal.another.Result.Error -> onFailed(a.message)
                        else -> {}
                    }
                }
                false
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.favorite_activity -> {
                val intent = Intent(applicationContext, FavoriteActivity::class.java)
                startActivity(intent)
                true
            }




            R.id.setting_activity -> {
                val intent = Intent(applicationContext, SettingActivity::class.java)
                startActivity(intent)
                true
            }

            else -> {
                false
            }
        }
    }
//tes
    override fun onLoading() {
        binding.initSearch.apply {
            progressBar.visibility = visible
            rvListUser.visibility = gone
            notFound.visibility = gone

        }
    }

    override fun onSuccess(data: List<AppEntity>) {
        userAdapter.setAllData(data)
        binding.initSearch.apply {
            progressBar.visibility = gone
            notFound.visibility = gone
            rvListUser.visibility = visible
        }
    }

    override fun onFailed(message: String?) {
        binding.initSearch.apply {
            if (message == null) {
                notFound.apply {
                    text = resources.getString(R.string.user_not_found)
                    visibility = visible
                    progressBar.visibility = gone
                }
            } else {
                false
            }
        }
    }
}