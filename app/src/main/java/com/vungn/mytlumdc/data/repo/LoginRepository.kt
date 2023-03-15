package com.vungn.mytlumdc.data.repo

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.vungn.mytlumdc.util.PreferenceKeys
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginRepository @Inject constructor(private val dataStore: DataStore<Preferences>) {
    suspend fun login(username: String, password: String): Boolean {
        return withContext(Dispatchers.IO) {
            return@withContext if (username.trim() == "vu" && password.trim() == "vu") {
                dataStore.edit { pref ->
                    pref[PreferenceKeys.ID] = username.trim()
                    pref[PreferenceKeys.TOKEN] = "0000"
                }
                true
            } else {
                false
            }
        }
    }
}