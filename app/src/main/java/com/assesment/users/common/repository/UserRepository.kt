package com.assesment.users.common.repository


import com.assesment.users.common.models.User
import com.assesment.users.host.viewmodel.HostViewModel
import com.google.gson.Gson
import org.koin.dsl.module
import java.lang.Exception


val userRepositoryModule = module {
    single { UserRepository(get()) }
}

class UserRepository(private val hostViewModel: HostViewModel) {

    fun getUsers(): List<User> {
        return hostViewModel.usersStorage.users ?: emptyList()
    }

    fun addUser(user: User) : Boolean {
        // Returned boolean value as simulation of success/error
        return try {
            hostViewModel.usersStorage.users?.add(user) ?: run {
                hostViewModel.usersStorage.users = arrayListOf()
                hostViewModel.usersStorage.users?.add(user)
            }
            true
        } catch (e: Exception){
            false
        }
    }

    fun updateUser(user: User) {

        hostViewModel.usersStorage.users?.map {
            if (it.id == user.id) {
                it.avatarId = user.avatarId
                it.name = user.name
                it.bio = user.bio
            }
        }
        printUserJsonData()
    }

     fun removeUser(user: User) {

      hostViewModel.usersStorage.users?.minusElement(user)

        printUserJsonData()
    }



    private  fun printUserJsonData() {
        println("_____________________________\n" +
                Gson().toJson(hostViewModel.usersStorage)
        )
    }
}