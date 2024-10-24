package com.kmp.encryption.utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.byteArrayPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class DataStoreManager(
    private val dataStore: DataStore<Preferences>,
    private val encryptionManager: EncryptionManager
) {
    suspend fun saveData(key: String, value: String) {
        val encryptedString = encryptionManager.encrypt(value)

        dataStore.edit { mutablePreferences ->
            mutablePreferences[byteArrayPreferencesKey(key)] = encryptedString
        }
    }

    suspend fun getData(key: String): String {
        val encryptedString = dataStore.data.map { preferences ->
            preferences[byteArrayPreferencesKey(key)]
        }.first()

        return encryptedString?.let { encryptionManager.decrypt(it) } ?: ""
    }
}