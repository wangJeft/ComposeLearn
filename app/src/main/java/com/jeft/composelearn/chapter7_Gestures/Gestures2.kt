package com.jeft.composelearn.chapter7_Gestures

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollDispatcher
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlin.math.roundToInt


@Composable
fun Gestures2() {
    Box {
//        CombinedClickDemo()
//        DraggableDemo()
//        SwipeDemo()
//        TransformerDemo()
        SmartSwipeRefreshDemo()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CombinedClickDemo() {
    val enableState by remember {
        mutableStateOf(true)
    }
    Box(
        modifier = Modifier
            .size(200.dp)
            .background(Color.Green)
            .combinedClickable(enabled = enableState,
                onClick = {
                    Log.d("Gestures2", "onClick: ")
                },
                onLongClick = {
                    Log.d("Gestures2", "onLongClick: ")
                },
                onDoubleClick = {
                    Log.d("Gestures2", "onDoubleClick: ")
                })
    )
}

@Composable
fun DraggableDemo() {
    var offsetX by remember {
        mutableStateOf(0f)
    }
    val boxSideLengthDp = 50.dp
    val boxSideLengthPx = with(LocalDensity.current) {
        boxSideLengthDp.toPx()
    }
    val draggableState = rememberDraggableState {
        offsetX = (offsetX + it).coerceIn(0f, 3 * boxSideLengthPx)
    }
    Box(
        modifier = Modifier
            .width(boxSideLengthDp * 4)
            .height(boxSideLengthDp)
            .background(Color.LightGray)
    ) {
        Box(modifier = Modifier
            .size(boxSideLengthDp)
            .offset { IntOffset(offsetX.roundToInt(), 0) }
            .draggable(orientation = Orientation.Horizontal, state = draggableState)
            .background(Color.DarkGray))
    }
}

enum class Status {
    CLOSE, OPEN
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeDemo() {
    val blockSize = 48.dp
    val blockSizePx = with(LocalDensity.current) { blockSize.toPx() }
    val swipeableState = rememberSwipeableState(initialValue = Status.CLOSE)
    val anchors = mapOf(0f to Status.CLOSE, blockSizePx to Status.OPEN)
    Box(
        modifier = Modifier
            .size(height = blockSize, width = blockSize * 2)
            .background(Color.LightGray)
    ) {
        Box(modifier = Modifier
            .offset { IntOffset(swipeableState.offset.value.toInt(), 0) }
            .swipeable(
                state = swipeableState,
                anchors = anchors,
                thresholds = { from, _ ->//不同锚点之间吸附的临界阈值
                    if (from == Status.CLOSE) {
                        FractionalThreshold(0.3f)
                    } else {
                        FractionalThreshold(0.5f)
                    }
                },
                orientation = Orientation.Horizontal
            )
            .size(blockSize)
            .background(Color.DarkGray)
        )
    }
}

@Composable
fun TransformerDemo() {
    val boxSize = 100.dp
    var offset by remember {
        mutableStateOf(Offset.Zero)
    }
    var rotationAngle by remember {
        mutableStateOf(0f)
    }
    var scale by remember {
        mutableStateOf(1f)
    }
    val transformableState = rememberTransformableState(onTransformation = { zoomChange, panChange, rotationChange ->
        scale *= zoomChange
        offset += panChange
        rotationAngle += rotationChange
    })

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Box(modifier = Modifier
            .size(boxSize)
            .rotate(rotationAngle)//rotate要在offset前调用
            .offset { IntOffset(offset.x.roundToInt(), offset.y.roundToInt()) }
            .scale(scale)
            .background(Color.Green)
            .transformable(state = transformableState, lockRotationOnZoomPan = false)) {
        }
    }
}

class SmartSwipeRefreshState {
    private val indicatorOffsetAnimatable = Animatable(0.dp, Dp.VectorConverter)
    val indicatorOffset get() = indicatorOffsetAnimatable.value
    private val _indicatorOffset = MutableStateFlow(0f)
    val indicatorOffsetFlow: Flow<Float> get() = _indicatorOffset
    val isSwipeInProgress by derivedStateOf { indicatorOffset != 0.dp }

    var isRefreshing: Boolean by mutableStateOf(false)

    fun updateOffsetDelta(value: Float) {
        _indicatorOffset.value = value
    }

    suspend fun snapToOffset(value: Dp) {
        indicatorOffsetAnimatable.snapTo(value)
    }

    suspend fun animateToOffset(value: Dp) {
        indicatorOffsetAnimatable.animateTo(value, tween(1000))
    }
}

private class SmartSwipeRefreshNestedScrollConnection(val state: SmartSwipeRefreshState, val height: Dp) : NestedScrollConnection {
    override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
        return super.onPostFling(consumed, available)
    }

    override fun onPostScroll(consumed: Offset, available: Offset, source: NestedScrollSource): Offset {
        return if (source == NestedScrollSource.Drag && available.y > 0) {
            state.updateOffsetDelta(available.y)
            Offset(x = 0f, y = available.y)
        } else {
            Offset.Zero
        }
    }

    override suspend fun onPreFling(available: Velocity): Velocity {
        if (state.indicatorOffset > height / 2) {
            state.animateToOffset(height)
            state.isRefreshing = true
        } else {
            state.animateToOffset(0.dp)
        }
        return super.onPreFling(available)
    }

    override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
        return if (source == NestedScrollSource.Drag && available.y < 0) {
            state.updateOffsetDelta(available.y)
            if (state.isSwipeInProgress) Offset(x = 0f, y = available.y) else Offset.Zero
        } else {
            Offset.Zero
        }
    }
}

@Composable
fun SubComposeSmartSwipeRefresh(indicator: @Composable () -> Unit, content: @Composable (height: Dp) -> Unit) {
    SubcomposeLayout { constraints: Constraints ->
        var indicatorPlaceable = subcompose("indicator", indicator).first().measure(constraints)
        var contentPlaceable = subcompose("content") { content(indicatorPlaceable.height.toDp()) }.map { it.measure(constraints) }.first()
        layout(contentPlaceable.width, contentPlaceable.height) {
            contentPlaceable.placeRelative(0, 0)
        }
    }
}

@Composable
fun SmartSwipeRefreshDemo() {
    SmartSwipeRefresh(onRefresh = {}) {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            repeat(20){
                DraggableDemo()
            }
        }
    }
}

@Composable
fun SmartSwipeRefresh(
    onRefresh: suspend () -> Unit, state: SmartSwipeRefreshState = remember {
        SmartSwipeRefreshState()
    },
    loadingIndicator: @Composable () -> Unit = { CircularProgressIndicator() },
    content: @Composable () -> Unit
) {
    SubComposeSmartSwipeRefresh(indicator = loadingIndicator) { height ->
        val smartSwipeRefreshNestedScrollConnection = remember(state, height) {
            SmartSwipeRefreshNestedScrollConnection(state, height)
        }
        Box(modifier = Modifier.nestedScroll(smartSwipeRefreshNestedScrollConnection), contentAlignment = Alignment.Center) {
            Box(modifier = Modifier.offset(y = -height + state.indicatorOffset)) {
                loadingIndicator()
            }
            Box(modifier = Modifier.offset(y = state.indicatorOffset)) {
                content()
            }
        }
        var density = LocalDensity.current
        LaunchedEffect(Unit) {
            state.indicatorOffsetFlow.collect {
                var currentOffset = with(density) { state.indicatorOffset + it.toDp() }
                state.snapToOffset(currentOffset.coerceAtLeast(0.dp).coerceAtMost(height))
            }
        }
        LaunchedEffect(state.isRefreshing) {
            if (state.isRefreshing) {
                onRefresh()
                state.animateToOffset(0.dp)
                state.isRefreshing = false
            }
        }
    }
}