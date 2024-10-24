package com.kmp.encryption.utils

interface EncryptionManager {
    suspend fun encrypt(plainText: String) : ByteArray
    suspend fun decrypt(cipherText: ByteArray): String
}