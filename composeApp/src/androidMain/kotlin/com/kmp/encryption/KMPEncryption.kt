package com.kmp.encryption

import android.app.Application
import com.kmp.encryption.di.appModule
import com.kmp.encryption.di.sharedModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class KMPEncryption: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@KMPEncryption)
            modules(appModule, sharedModule)
        }
    }
}