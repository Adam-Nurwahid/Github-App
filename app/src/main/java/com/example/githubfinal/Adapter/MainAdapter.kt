// MainAdapter.kt
package com.example.githubfinal.Adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubfinal.Activity.DetailActivity
import com.example.githubfinal.another.Constant.EXTRA_ACCOUNT
import com.example.githubfinal.database.room.AppEntity
import com.example.githubfinal.databinding.ItemRowBinding




class MainAdapter :  RecyclerView.Adapter<MainAdapter.UserViewHolder>(){
    private val listuser = ArrayList<AppEntity>()

    inner class UserViewHolder(private val view: ItemRowBinding) :
        RecyclerView.ViewHolder(view.root) {

        fun bind(user: AppEntity) {
            view.apply {
                tvNameList.text = user.username
                Glide.with(itemView.context)
                    .load(user.avatar)
                    .into(imgList)
            }

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra(EXTRA_ACCOUNT, user.username)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(listuser[position])
    }

    override fun getItemCount(): Int = listuser.size

    @SuppressLint("NotifyDataSetChanged")
    fun setAllData(data: List<AppEntity>) {
        listuser.apply {
            clear()
            addAll(data)
        }
        notifyDataSetChanged()
    }
}