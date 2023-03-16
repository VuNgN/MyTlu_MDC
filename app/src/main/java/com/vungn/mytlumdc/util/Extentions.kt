package com.vungn.mytlumdc.util

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat

fun Activity.hideSoftKeyboard() {
    currentFocus?.let {
        val inputMethodManager =
            ContextCompat.getSystemService(this, InputMethodManager::class.java)!!
        inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
    }
}

fun Context.isDarkThemeOn(): Boolean {
    return (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
}
