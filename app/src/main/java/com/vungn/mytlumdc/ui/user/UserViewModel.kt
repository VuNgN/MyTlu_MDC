package com.vungn.mytlumdc.ui.user

import androidx.lifecycle.LiveData
import com.vungn.mytlumdc.data.model.UserPreference
import com.vungn.mytlumdc.ui.user.impl.LoginResult
import kotlinx.coroutines.flow.MutableStateFlow

interface UserViewModel {
    val user: LiveData<UserPreference?>
    fun login(username: String, password: String): MutableStateFlow<LoginResult>
}