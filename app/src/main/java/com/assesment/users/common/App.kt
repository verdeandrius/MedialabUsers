package com.assesment.users.common

import android.app.Application
import com.assesment.users.common.repository.userRepositoryModule
import com.assesment.users.home.viewmodel.homeViewModelModule
import com.assesment.users.host.viewmodel.hostViewModelModule
import com.assesment.users.profile.viewmodel.profileViewModelModule
import com.facebook.drawee.backends.pipeline.Fresco

import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initializeKoin()
        Fresco.initialize(this)
    }

    private fun initializeKoin() {
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    hostViewModelModule,
                    userRepositoryModule,
                    homeViewModelModule,
                    profileViewModelModule,
                )
            )
        }
    }
}