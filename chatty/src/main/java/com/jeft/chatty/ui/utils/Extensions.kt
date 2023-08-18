package com.jeft.chatty.ui.utils

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.activity.ComponentActivity

fun Context.hideIME() {
    (getSystemService(ComponentActivity.INPUT_METHOD_SERVICE) as InputMethodManager)
        .hideSoftInputFromWindow((this as Activity).currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
}