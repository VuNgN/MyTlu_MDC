package com.vungn.mytlumdc.ui.user.impl

import android.util.Log
import androidx.lifecycle.*
import com.vungn.mytlumdc.data.model.UserPreference
import com.vungn.mytlumdc.data.repo.LoginRepository
import com.vungn.mytlumdc.data.repo.UserRepository
import com.vungn.mytlumdc.ui.user.UserViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class UserViewModelImpl @Inject constructor(
    private val loginRepository: LoginRepository,
    userRepository: UserRepository
) :
    UserViewModel, ViewModel() {
    private val _user: MutableLiveData<UserPreference?> =
        userRepository.userPreferenceFlow.asLiveData() as MutableLiveData<UserPreference?>
    private val _loginResult: MutableStateFlow<LoginResult> = MutableStateFlow(LoginResult(false))
    override val user: LiveData<UserPreference?>
        get() = _user

    override fun login(username: String, password: String): MutableStateFlow<LoginResult> {
        Log.d(TAG, "Login info: ${username.trim()} - ${password.trim()}")
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val isLoggedIn = loginRepository.login(username, password)
                if (isLoggedIn) {
                    _user.postValue(UserPreference(username, ""))
                    _loginResult.value = LoginResult(true)
                } else {
                    _loginResult.value = LoginResult(false)
                }
            } catch (e: IOException) {
                //
            }
        }
        return _loginResult
    }

    companion object {
        private val TAG = UserViewModelImpl::class.simpleName
    }
}

data class LoginResult(val success: Boolean)
