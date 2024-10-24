package com.kmp.encryption.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kmp.encryption.utils.Constants.DATASTORE_PREF_USERTOKEN_KEY
import com.kmp.encryption.utils.DataStoreManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserViewModel(
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _userToken = MutableStateFlow("")
    val userToken: StateFlow<String> get() = _userToken.asStateFlow()

    fun storeUserToken(token: String) {
        viewModelScope.launch {
            dataStoreManager.saveData(token, DATASTORE_PREF_USERTOKEN_KEY)
        }

    }

    fun getUserToken() {
        viewModelScope.launch {
            _userToken.value = dataStoreManager.getData(DATASTORE_PREF_USERTOKEN_KEY)
        }
    }
}