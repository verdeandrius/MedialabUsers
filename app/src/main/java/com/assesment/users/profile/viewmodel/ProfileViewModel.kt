package com.assesment.users.profile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assesment.users.common.models.User
import com.assesment.users.common.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.dsl.module

val profileViewModelModule = module {
    single { ProfileViewModel(get()) }
}

class ProfileViewModel(private val repository: UserRepository) : ViewModel() {

    private val _isUserAdded = MutableLiveData<Boolean>()
    val isUserAdded: LiveData<Boolean> get() = _isUserAdded

    fun addUser(user: User) = viewModelScope.launch(Dispatchers.IO) {
        _isUserAdded.postValue(repository.addUser(user))
    }

    fun updateUser(user: User) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateUser(user)
    }


}