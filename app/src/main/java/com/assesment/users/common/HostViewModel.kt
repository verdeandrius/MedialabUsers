package com.assesment.users.common

import androidx.lifecycle.ViewModel
import com.assesment.users.common.models.UsersStorage
import org.koin.dsl.module

val hostViewModelModule = module {
    single { HostViewModel() }
}

class HostViewModel: ViewModel() {
    val usersStorage = UsersStorage()
}