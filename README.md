# Securing Data in KMP: Cross-Platform Encryption Strategies for Local Storage

This project explores encryption techniques for securing local data in Kotlin Multiplatform (KMP) projects, focusing on cross-platform strategies. It demonstrates how to handle sensitive information on both Android and iOS by leveraging platform-specific encryption libraries like `CoreCrypto` for iOS and `Cipher` & `Keystore` for Android. 
The goal is to achieve seamless data security without sacrificing cross-platform flexibility.

## Libraries Used
- `KOIN` - For dependency injection
- `DATASTORE` - For local storage
- `Keystore` - to store cryptographic keys in a container
- `CoreCrypto`- for cryptographic operations in iOS
- `Cipher` - to perform encryption and decryption operations using various algorithms (such as AES, RSA, and DES)

## Encryption in Shared Module
`composeApp/src/commonMain/kotlin/com/kmp/encryption/utils/EncryptionManager.kt`

This is an interface that defines the encryption and decryption methods.

```
interface EncryptionManager {
    suspend fun encrypt(plainText: String) : ByteArray
    suspend fun decrypt(cipherText: ByteArray): String
}
```

Their implementation is done in the native Android & iOS modules
- Android Module: `composeApp/src/androidMain/kotlin/com/kmp/encryption/utils/AndroidEncryptionManager.kt`
- iOS Module: `composeApp/src/iosMain/kotlin/com/kmp/encryption/IosEncryptionManager.kt`

## Local Storage in Shared Module
`composeApp/src/commonMain/kotlin/com/kmp/encryption/utils/DataStoreManager.kt`

This is a class that uses dataStore to store/retrieve data locally. DataStore is instantiated in the native Android and iOS modules through Koin.

- Android Module: `composeApp/src/androidMain/kotlin/com/kmp/encryption/di/AppModule.android.kt`
- 
```
single<DataStore<Preferences>> {
        val context = get<Context>()
        PreferenceDataStoreFactory.createWithPath(produceFile = {
            context.filesDir.resolve(DATASTORE_PREF_FILENAME).absolutePath.toPath()
        })
    }
```

- iOS Module : `composeApp/src/iosMain/kotlin/com/kmp/encryption/di/AppModule.ios.kt`

```
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
```
Presentation slides for this project are available [here](https://www.canva.com/design/DAGUe3Wq9ew/25yZmZ23AiGrFEqpyUHV5Q/edit?utm_content=DAGUe3Wq9ew&utm_campaign=designshare&utm_medium=link2&utm_source=sharebutton)
