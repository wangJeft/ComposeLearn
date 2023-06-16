package com.jeft.composelearn.gestures

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jeft.composelearn.ui.theme.TAG


@Composable
fun Gesture() {
    ClickGestures()
}

@Preview
@Composable
fun ClickGestures() {
    Box {
        val count = remember {
            mutableStateOf(0)
        }
        Text(text = count.value.toString(), textAlign = TextAlign.Center, modifier = Modifier
            .align(alignment = Alignment.Center)
            .background(Color.Gray)
            .size(100.dp)
            .wrapContentHeight()
            .clickable {
                count.value += 1
            }
            .pointerInput(Unit) {
                detectTapGestures(onDoubleTap = {
                    Log.e(TAG, "onDoubleTap: ")
                }, onLongPress = {
                    Log.e(TAG, "onLongPress: ")
                }, onPress = {
                    Log.e(TAG, "onPress: ")
                }, onTap = {
                    Log.e(TAG, "onTap: ")
                })
            })
    }
}