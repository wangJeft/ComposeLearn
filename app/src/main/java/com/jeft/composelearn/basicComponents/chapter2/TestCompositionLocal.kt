package com.jeft.composelearn.basicComponents.chapter2

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color

@Composable
fun TestCompositionLocal() {
    val currentLocalColor = compositionLocalOf { Color.Black }
    val color by remember {
        mutableStateOf(Color.Green)
    }
    CompositionLocalProvider(currentLocalColor provides color) {
    }
}