package com.assesment.users.home.view

import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.assesment.users.common.models.User

class UserRecyclerViewAdapter(private val navController: NavController) :
    ListAdapter<User, UserItemViewHolder>(USER_COMPARATOR) {

    lateinit var onUserSelectedListener : OnUserSelectedListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserItemViewHolder {
        return UserItemViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: UserItemViewHolder, position: Int) {
        val repoData = getItem(position)
        if (repoData != null) {
            holder.bind(repoData, onUserSelectedListener, navController)
        }
    }

    companion object {
        private val USER_COMPARATOR = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
                oldItem == newItem
        }
    }
}