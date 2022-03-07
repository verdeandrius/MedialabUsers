package com.assesment.users.common.models

import java.util.*
import kotlin.random.Random

data class User(
    var name: String,
    var avatarId: Int,
    var bio: String,
    val id: String = UUID.randomUUID().toString(),
)
