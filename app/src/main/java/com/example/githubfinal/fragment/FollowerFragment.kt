package com.example.githubfinal.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubfinal.Adapter.MainAdapter
import com.example.githubfinal.R
import com.example.githubfinal.another.Conditions
import com.example.githubfinal.another.Constant.EXTRA_USERNAME
import com.example.githubfinal.database.room.AppEntity
import com.example.githubfinal.databinding.FragmentFollowerBinding
import com.example.githubfinal.model.DetailViewModel


class FollowerFragment : Fragment(), Conditions<List<AppEntity>> {
    private var _binding:FragmentFollowerBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailViewModel by viewModels()
    private lateinit var userAdapter: MainAdapter
    private var username: String? = null

    companion object {
        fun getInstance(username: String): Fragment = FollowerFragment().apply {
            arguments = Bundle().apply {
                putString(EXTRA_USERNAME, username)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(EXTRA_USERNAME)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFollowerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userAdapter = MainAdapter()
        binding.rvListUser.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        viewModel.getFollowers(username.toString()).observe(viewLifecycleOwner) {
            when (it) {
                is com.example.githubfinal.another.Result.Error -> onFailed(it.message)
                is com.example.githubfinal.another.Result.Loading -> onLoading()
                is com.example.githubfinal.another.Result.Success -> it.data?.let { it1 -> onSuccess(it1) }
            }
        }
    }

    override fun onLoading() {
        binding.apply {
            tvMessage.visibility = gone
            progressBar.visibility = visible
            rvListUser.visibility = gone
        }
    }

    override fun onSuccess(data: List<AppEntity>) {
        userAdapter.setAllData(data)
        binding.apply {
            tvMessage.visibility = gone
            progressBar.visibility = gone
            rvListUser.visibility = visible
        }
    }

    override fun onFailed(message: String?) {
        binding.apply {
            if (message == null) {
                tvMessage.text = resources.getString(R.string.followers_not_found, username)
                tvMessage.visibility = visible
            } else {
                tvMessage.text = resources.getString(R.string.no_internet)
                tvMessage.visibility = visible
            }
            progressBar.visibility = gone
            rvListUser.visibility = gone
        }
    }
}