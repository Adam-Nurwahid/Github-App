package com.example.githubfinal.Activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubfinal.Adapter.FragmentAdapter
import com.example.githubfinal.R
import com.example.githubfinal.another.Conditions
import com.example.githubfinal.another.Constant.EXTRA_ACCOUNT
import com.example.githubfinal.another.Constant.TAB_TITLES
import com.example.githubfinal.database.room.AppEntity
import com.example.githubfinal.databinding.ActivityDetailBinding
import com.example.githubfinal.model.DetailViewModel
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity(), Conditions<AppEntity?> {
    private lateinit var binding: ActivityDetailBinding
    private val viewModel by viewModels<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val username = intent.getStringExtra(EXTRA_ACCOUNT)
        val pageAdapter = FragmentAdapter(this, username.toString())

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getUser(username.toString()).observe(this@DetailActivity) {
                when (it) {
                    is com.example.githubfinal.another.Result.Error -> onFailed(it.message)
                    is com.example.githubfinal.another.Result.Loading -> onLoading()
                    is com.example.githubfinal.another.Result.Success -> onSuccess(it.data)
                }
            }
        }

        binding.apply {
            viewPager.adapter = pageAdapter
            TabLayoutMediator(tabs, viewPager) { tabs, position ->
                tabs.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }
    }

    override fun onLoading() {
        binding.apply {
            fabFavorite.visibility = visible
            pbDetail.visibility = visible
        }
    }

    override fun onSuccess(data: AppEntity?) {
        binding.apply {
            pbDetail.visibility = gone

            Glide.with(applicationContext)
                .load(data?.avatar)
                .apply(RequestOptions.circleCropTransform())
                .into(imgDetail)

            nameDetail.text = data?.name
            emailDetail.text = data?.username
            followerDetail.text = data?.follower.toString()
            followingDetail.text = data?.following.toString()

            if (data?.isFavorite == true) {
                fabFavorite.setImageResource(R.drawable.favorite_fill)
            } else {
                fabFavorite.setImageResource(R.drawable.favorite_border)
            }

            fabFavorite.setOnClickListener {
                if (data?.isFavorite == true) {
                    data.isFavorite = false
                    viewModel.deleteFromFavorite(data)
                    fabFavorite.setImageResource(R.drawable.favorite_border)

                } else {
                    data?.isFavorite = true
                    data?.let { user -> viewModel.addToFavorite(user) }
                    fabFavorite.setImageResource(R.drawable.favorite_fill)

                }
            }
        }
    }

    override fun onFailed(message: String?) {
        binding.apply {
            fabFavorite.visibility = gone
            fabFavorite.visibility = gone
        }
    }
}