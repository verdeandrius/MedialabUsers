package com.assesment.users.home.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.assesment.users.R
import com.assesment.users.common.*
import com.assesment.users.common.models.User
import com.assesment.users.databinding.ItemUserListBinding
import com.assesment.users.profile.UPDATE_MODE
import com.assesment.users.profile.USER_DATA

class UserItemViewHolder(private val binding: ItemUserListBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        user: User,
        userSelectedListener: OnUserSelectedListener,
        navController: NavController
    ) {
        binding.apply {
            // Data
            tvName.text = user.name
            tvBio.text = user.bio
            ivAvatar.setImageURI(getAvatarImageUrl(user.avatarId))

            // OnClickListeners
            cvUser.apply {
                setOnClickListener {
                    navController.navigate(
                        R.id.profileFragment, bundleOf(
                            UPDATE_MODE to false,
                            USER_DATA to user.toString()
                        )
                    )
                }
                setOnLongClickListener {
                    userSelectedListener.onUserSelected(user, binding)
                    true
                }
            }
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

    private fun getAvatarImageUrl(avatarId: Int) = when(avatarId) {
        1 -> AVATAR_1_URL
        2 -> AVATAR_2_URL
        3 -> AVATAR_3_URL
        4 -> AVATAR_4_URL
        5 -> AVATAR_5_URL
        else -> AVATAR_6_URL
    }
}