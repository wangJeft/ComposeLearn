package com.jeft.composelearn.chapter7_Gestures

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun Gestures3() {
//    GestureDragSample2()
    NestedBoxDemo()
}

@Preview
@Composable
fun GestureDragSample() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        var offsetX by remember {
            mutableStateOf(0f)
        }

        var offsetY by remember {
            mutableStateOf(0f)
        }

        var rotationAngle by remember {
            mutableStateOf(0f)
        }
        var scale by remember {
            mutableStateOf(1f)
        }

        Box(modifier = Modifier
            .rotate(rotationAngle)
            .scale(scale)
            .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
            .background(Color.Blue)
            .size(100.dp)
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    offsetX += dragAmount.x
                    offsetY += dragAmount.y
                }

                detectDragGesturesAfterLongPress { change, dragAmount ->
                }
                detectHorizontalDragGestures { change, dragAmount ->
                }
                detectHorizontalDragGestures(onDragStart = { offset ->
                }, onDragEnd = {}, onDragCancel = {}, onHorizontalDrag = { change, dragAmount ->
                })
                detectVerticalDragGestures { change, dragAmount ->
                }
                detectVerticalDragGestures(onDragStart = { offset ->
                }, onDragEnd = {}, onDragCancel = {}, onVerticalDrag = { change, dragAmount ->
                })

                detectTransformGestures(true) { centroid: Offset, pan: Offset, zoom: Float, rotation: Float ->

                }

            }, contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Drag me", color = Color.White
            )
        }
    }
}

@Composable
fun GestureDragSample2() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        var offset by remember {
            mutableStateOf(Offset.Zero)
        }

        var rotationAngle by remember {
            mutableStateOf(0f)
        }
        var scale by remember {
            mutableStateOf(1f)
        }

        Box(modifier = Modifier
            .rotate(rotationAngle)
            .scale(scale)
            .offset { IntOffset(offset.x.roundToInt(), offset.y.roundToInt()) }
            .background(Color.Blue)
            .size(100.dp)
            .pointerInput(Unit) {
                detectTransformGestures(false) { centroid: Offset, pan: Offset, zoom: Float, rotation: Float ->
                    offset += pan
                    scale *= zoom
                    rotationAngle += rotation
                }

            }, contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Drag me", color = Color.White
            )
        }
    }
}

@Composable
fun NestedBoxDemo() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
/*
        Box(modifier = Modifier
            .rotate(rotationAngle)
            .scale(scale)
            .offset { IntOffset(offset.x.roundToInt(), offset.y.roundToInt()) }
            .background(Color.Blue)
            .size(100.dp)
            .pointerInput(Unit) {
                awaitPointerEventScope {

                    val pass = PointerEventPass.Final*//*

                    var event = awaitPointerEvent()
                }
            }, contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Drag me", color = Color.White
            )
        }*/

        /*  //手势事件会在所有使用Initial参数的组件间自上而下的完成首次分发
        val pass = PointerEventPass.Initial

        //手势事件会在所有使用Main参数的组件间自下而上的完成第二次分发
        val pass = PointerEventPass.Main

        //手势事件会在所有使用Final参数的组件间自上而下的完成最后一次分发,*/

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(400.dp)
                .background(Color.Red)
                .pointerInput(Unit) {
                    awaitPointerEventScope {
                        val event = awaitPointerEvent(PointerEventPass.Initial)
                        Log.d("NestedBoxDemo", "first layer, downChange: ${event.changes[0].consumed.downChange}")
                    }
                }
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(200.dp)
                    .background(Color.Blue)
                    .pointerInput(Unit) {
                        awaitPointerEventScope {
                            val event = awaitPointerEvent(PointerEventPass.Final)
                            Log.d("NestedBoxDemo", "second layer, downChange: ${event.changes[0].consumed.downChange}")
                        }
                    }
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color.Green)
                        .pointerInput(Unit) {
                            awaitPointerEventScope {
                                val event = awaitPointerEvent(PointerEventPass.Main)
                                Log.d("NestedBoxDemo", "third layer, downChange: ${event.changes[0].consumed.downChange}")
                            }
                        }
                ) {
                    /*Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(50.dp)
                            .background(Color.White)
                            .pointerInput(Unit) {
                                awaitPointerEventScope {
                                    val event = awaitPointerEvent(PointerEventPass.Main)
                                    Log.d("NestedBoxDemo", "fourth layer")
                                }
                            }
                    ) {

                    }*/
                }
            }
        }
    }
}