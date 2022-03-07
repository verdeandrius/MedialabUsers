package com.assesment.users.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assesment.users.common.models.User
import com.assesment.users.common.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.dsl.module

val homeViewModelModule = module {
    single { HomeViewModel(get()) }
}

class HomeViewModel(private val repository: UserRepository) : ViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> get() = _users

    fun getUsers() = viewModelScope.launch(Dispatchers.IO) {
        _users.postValue(repository.getUsers())
    }

    fun removeUser(user: User) = viewModelScope.launch(Dispatchers.IO) {
        repository.removeUser(user)
    }

}