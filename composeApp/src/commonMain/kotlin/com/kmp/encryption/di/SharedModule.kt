package com.kmp.encryption.di

import com.kmp.encryption.utils.DataStoreManager
import org.koin.dsl.module

val sharedModule = module {

    single<DataStoreManager> {
        DataStoreManager(
            dataStore = get(),
            encryptionManager = get()
        )
    }

}