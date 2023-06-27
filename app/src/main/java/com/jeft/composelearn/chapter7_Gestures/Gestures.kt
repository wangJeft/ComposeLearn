package com.jeft.composelearn.chapter7_Gestures

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Text
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.jeft.composelearn.ui.theme.TAG
import kotlin.math.roundToInt


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

@Preview
@Composable
fun ScrollableSample() {
    var offset by remember {
        mutableStateOf(0f)
    }
    Box(
        modifier = Modifier
            .size(150.dp)
            .scrollable(orientation = Orientation.Horizontal, state = rememberScrollableState { delta ->
                offset += delta
                delta
            })
            .background(Color.LightGray), contentAlignment = Alignment.Center
    ) {
        Text(text = offset.toString())
    }
}

/**
 * 自动嵌套滚动
 */
@Preview
@Composable
fun AutoNestedScroll() {
    Box(
        modifier = Modifier
            .background(Color.LightGray)
            .verticalScroll(rememberScrollState())
            .padding(32.dp)
    ) {
        Column {
            repeat(6) {
                Box(
                    modifier = Modifier
                        .height(128.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = "scroll here", Modifier
                            .border(12.dp, Color.DarkGray)
                            .background(Brush.verticalGradient(0f to Color.Gray, 1000f to Color.White))
                            .padding(24.dp)
                            .height(150.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun DragSample() {
    var offsetX by remember {
        mutableStateOf(0f)
    }
    var offsetY by remember {
        mutableStateOf(0f)
    }
    Text(text = "Drag me", modifier = Modifier
        .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
        .draggable(orientation = Orientation.Horizontal, state = rememberDraggableState { delta ->
            offsetX += delta
        })
    )
}

@Preview
@Composable
fun DragSample2() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        var offsetX by remember {
            mutableStateOf(0f)
        }

        var offsetY by remember {
            mutableStateOf(0f)
        }
        Box(modifier = Modifier
            .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
            .background(Color.Blue)
            .size(100.dp)
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    offsetX += dragAmount.x
                    offsetY += dragAmount.y
                }
            }, contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Drag me", color = Color.White
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun SwipeableSample() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        val width = 96.dp
        val squareSize = 48.dp
        val swipeableState = rememberSwipeableState(initialValue = 0)
        val sizePx = with(LocalDensity.current) { squareSize.toPx() }
        val anchors = mapOf(0f to 0, sizePx to 1)
        Box(
            modifier = Modifier
                .width(width)
                .swipeable(
                    state = swipeableState,
                    anchors = anchors,
                    thresholds = { _, _ -> FractionalThreshold(0.3f) },
                    orientation = Orientation.Horizontal
                )
                .background(Color.LightGray)
        ) {
            Box(modifier = Modifier
                .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
                .size(squareSize)
                .background(Color.DarkGray))
        }
    }
}