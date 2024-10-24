package com.kmp.encryption.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.kmp.encryption.utils.AndroidEncryptionManager
import com.kmp.encryption.utils.EncryptionManager
import com.kmp.encryption.utils.Constants.DATASTORE_PREF_FILENAME
import okio.Path.Companion.toPath
import org.koin.dsl.module

actual val appModule = module {

    single<EncryptionManager> {
        AndroidEncryptionManager(
            dataStoreManager = get()
        )
    }

    single<DataStore<Preferences>> {
        val context = get<Context>()
        PreferenceDataStoreFactory.createWithPath(produceFile = {
            context.filesDir.resolve(DATASTORE_PREF_FILENAME).absolutePath.toPath()
        })
    }

}