package com.assesment.users.common.models

import com.google.gson.Gson
import java.util.*

data class User(
    var name: String,
    var avatarUrl: String,
    var bio: String,
    val id: String = UUID.randomUUID().toString(),
) {
    override fun toString() : String = Gson().toJson(this)
}
