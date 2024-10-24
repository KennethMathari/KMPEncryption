package com.kmp.encryption.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.kmp.encryption.utils.EncryptionManager
import com.kmp.encryption.IosEncryptionManager
import com.kmp.encryption.utils.Constants.DATASTORE_PREF_FILENAME
import kotlinx.cinterop.ExperimentalForeignApi
import okio.Path.Companion.toPath
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

@OptIn(ExperimentalForeignApi::class)
actual val appModule = module {

    single<EncryptionManager> {
        IosEncryptionManager()
    }

    single<DataStore<Preferences>> {
        PreferenceDataStoreFactory.createWithPath(
            produceFile = {
                val directory = NSFileManager.defaultManager.URLForDirectory(
                    directory = NSDocumentDirectory,
                    inDomain = NSUserDomainMask,
                    appropriateForURL = null,
                    create = false,
                    error = null
                )

                val filePath = requireNotNull(directory).path()+"/$DATASTORE_PREF_FILENAME"
                filePath.toPath()
            }
        )
    }
}