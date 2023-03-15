package com.vungn.mytlumdc.data.repo

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import com.vungn.mytlumdc.data.model.UserPreference
import com.vungn.mytlumdc.util.PreferenceKeys
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class UserRepository @Inject constructor(dataStore: DataStore<Preferences>) {
    private val _userPreferenceFlow = dataStore.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { pref: Preferences ->
        val username: String? = pref[PreferenceKeys.ID]
        val token: String? = pref[PreferenceKeys.TOKEN]
        if (username == null || token == null) {
            null
        } else {
            UserPreference(
                username = username,
                token = token
            )
        }
    }

    val userPreferenceFlow = _userPreferenceFlow
}