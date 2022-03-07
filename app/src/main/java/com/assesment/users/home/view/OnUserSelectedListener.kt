package com.assesment.users.home.view

import com.assesment.users.common.models.User
import com.assesment.users.databinding.ItemUserListBinding

interface OnUserSelectedListener {

    fun onUserSelected(user: User, itemBinding: ItemUserListBinding)

}