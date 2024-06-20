package com.example.githubfinal.Adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.githubfinal.another.Constant.TAB_TITLES
import com.example.githubfinal.fragment.FollowerFragment
import com.example.githubfinal.fragment.FollowingFragment

class FragmentAdapter (activity: AppCompatActivity, private val username: String) :
    FragmentStateAdapter(activity) {
        override fun getItemCount(): Int = TAB_TITLES.size

        override fun createFragment(position: Int): Fragment {
            var fragment: Fragment? = null
            when (position) {
                0 -> fragment = FollowingFragment.getInstance(username)
                1 -> fragment = FollowerFragment.getInstance(username)
            }
            return fragment as Fragment
        }
    }