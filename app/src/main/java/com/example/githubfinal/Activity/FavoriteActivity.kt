package com.example.githubfinal.Activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubfinal.Adapter.MainAdapter
import com.example.githubfinal.R
import com.example.githubfinal.another.Conditions
import com.example.githubfinal.database.room.AppEntity
import com.example.githubfinal.databinding.ActivityFavoriteBinding
import com.example.githubfinal.model.FavoriteViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity(), Conditions<List<AppEntity>> {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var userAdapter: MainAdapter
    private val viewModel: FavoriteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Pengguna favorit"
        userAdapter = MainAdapter()

        binding.rvFavorite.apply {
            adapter = userAdapter
            layoutManager =
                LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        }

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getFavorites().observe(this@FavoriteActivity) {
                when (it) {
                    is com.example.githubfinal.another.Result.Loading -> onLoading()
                    is com.example.githubfinal.another.Result.Success -> it.data?.let { user ->
                        onSuccess(
                            user
                        )
                    }

                    is com.example.githubfinal.another.Result.Error -> onFailed(it.message)
                    else -> {}
                }
            }
        }
    }

    override fun onRestart() {
        super.onRestart()
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getFavorites().observe(this@FavoriteActivity) {
                when (it) {
                    is com.example.githubfinal.another.Result.Loading -> onLoading()
                    is com.example.githubfinal.another.Result.Success -> it.data?.let { user ->
                        onSuccess(
                            user
                        )
                    }

                    is com.example.githubfinal.another.Result.Error -> onFailed(it.message)
                    else -> {}
                }
            }
        }
    }

    override fun onLoading() {
        binding.apply {
            progressBar.visibility = visible
        }
    }

    override fun onSuccess(data: List<AppEntity>) {
        binding.apply {
            progressBar.visibility = gone
        }
        userAdapter.setAllData(data)
    }

    override fun onFailed(message: String?) {
        if (message == null) {
            binding.apply {
                progressBar.visibility = gone
                tvFavoriteError.text = getString(R.string.no_favorites_user)
                rvFavorite.visibility = gone
            }
        }
    }
}