package com.kmp.encryption.utils

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

class AndroidEncryptionManager(
    private val dataStoreManager: DataStoreManager
) : EncryptionManager {

    private val keyStore: KeyStore = KeyStore.getInstance("AndroidKeyStore").apply {
        load(null)
    }
    private val keyAlias = "EncryptionKey"

    init {
        if (!keyStore.containsAlias(keyAlias)) {
            val keyGenerator =
                KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
            keyGenerator.init(
                KeyGenParameterSpec.Builder(
                    keyAlias,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                ).setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE).build()
            )
            keyGenerator.generateKey()
        }
    }

    override suspend fun encrypt(plainText: String): ByteArray {
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.ENCRYPT_MODE, keyStore.getKey(keyAlias, null) as SecretKey)
        return cipher.doFinal(plainText.toByteArray())
    }

    override suspend fun decrypt(cipherText: ByteArray): String {
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.DECRYPT_MODE, keyStore.getKey(keyAlias, null) as SecretKey)
        return String(cipher.doFinal(cipherText))
    }
}