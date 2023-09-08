package com.jeft.chatty.screens.conversation

import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.staticCompositionLocalOf

@Composable
fun BackPressHandler(onBackPressed: () -> Unit) {
    val currentOnBackPressed by rememberUpdatedState(newValue = onBackPressed)
    val backCallback = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                currentOnBackPressed()
            }
        }
    }
    val backDispatcher = LocalBackPressedDispatcher.current
    DisposableEffect(key1 = backDispatcher) {
        backDispatcher.addCallback(backCallback)
        onDispose {
            backCallback.remove()
        }
    }
}

val LocalBackPressedDispatcher = staticCompositionLocalOf<OnBackPressedDispatcher> { error("No Back Dispatcher provided") }