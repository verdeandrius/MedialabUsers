package com.assesment.users.common.repository


import com.assesment.users.common.models.User
import com.assesment.users.host.viewmodel.HostViewModel
import org.koin.dsl.module


val userRepositoryModule = module {
    single { UserRepository(get()) }
}

class UserRepository(private val hostViewModel: HostViewModel) {

    fun getUsers(): List<User> {
        return hostViewModel.usersStorage.users ?: emptyList()
    }

    fun addUser(user: User): Boolean {
        // Returned boolean value as simulation of success/error
        return try {
            hostViewModel.usersStorage.users?.add(user) ?: run {
                hostViewModel.usersStorage.users = arrayListOf()
                hostViewModel.usersStorage.users?.add(user)
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    fun updateUser(user: User):User {
        hostViewModel.usersStorage.users?.map {
            if (it.id == user.id) {
                it.avatarUrl = user.avatarUrl
                it.name = user.name
                it.bio = user.bio
            }
        }
        return hostViewModel.usersStorage.users?.first { it.id == user.id }!!
    }

    fun removeUser(user: User) {
        hostViewModel.usersStorage.users?.minusElement(user)
    }
}