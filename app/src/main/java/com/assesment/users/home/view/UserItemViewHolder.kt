package com.assesment.users.home.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.assesment.users.R
import com.assesment.users.common.models.User
import com.assesment.users.databinding.ItemUserListBinding

class UserItemViewHolder(private val binding: ItemUserListBinding) :
    RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User, userSelectedListener: OnUserSelectedListener){
            binding.tvName.text = user.name
            binding.tvBio.text = user.bio

            binding.cvUser.setOnLongClickListener {
                userSelectedListener.onUserSelected(user, binding)
                true
            }

        }

    companion object {
        fun create(parent: ViewGroup): UserItemViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_user_list, parent, false)
            val binding = ItemUserListBinding.bind(view)
            return UserItemViewHolder(binding)
        }
    }
}