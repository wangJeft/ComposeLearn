package com.jeft.composelearn.chapter6_Animation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterExitState
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jeft.composelearn.ui.theme.getAnimationState

@Preview
@Composable
fun CrossfadeTest() {
    Column {
        Text(text = "Crossfade")
        AnimatedContentForCrossfade()
    }
}

@Preview
@Composable
fun AnimatedVisibilityTest() {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        Text(text = "使用visible控制内容动画")
        AnimatedVisibilityTest1()
        Text(text = "使用visibleState控制内容的状态")
        AnimatedVisibilityTest2()
        Text(text = "为子元素设置单独的动画")
        AnimatedVisibilityTest3()
        Text(text = "设置自定义的Transition")
        AnimatedVisibilityTest4()
    }
}

@Composable
fun AnimatedVisibilityTest1() {
    Column {
        var editable by remember { mutableStateOf(true) }
        val density = LocalDensity.current
        AnimatedVisibility(
            visible = editable,
            enter = slideInVertically { with(density) { -40.dp.roundToPx() } } + expandVertically(expandFrom = Alignment.Top) + fadeIn(initialAlpha = 0.3f),
            exit = slideOutVertically() + shrinkVertically() + fadeOut()
        ) {
            Text(
                text = "Hello", modifier = Modifier
                    .width(200.dp)
//                    .align(alignment = Alignment.CenterHorizontally)
                    .height(200.dp)
                    .background(Color.Magenta),
                textAlign = TextAlign.Center
            )
        }

        Button(onClick = { editable = !editable }) {
            Text(text = editable.toString())
        }
    }
}

@Composable
fun AnimatedVisibilityTest2() {
    Column {
        val state = remember {
            MutableTransitionState(true).apply { targetState = false }
        }
        AnimatedVisibility(
            visibleState = state,
            enter = slideInVertically() + expandVertically(expandFrom = Alignment.Top) + fadeIn(initialAlpha = 0.3f),
            exit = slideOutVertically() + shrinkVertically() + fadeOut()
        ) {
            Text(
                text = "Hello", modifier = Modifier
                    .width(200.dp)
//                    .align(alignment = Alignment.CenterHorizontally)
                    .height(200.dp)
                    .background(Color.Magenta),
                textAlign = TextAlign.Center
            )
        }

        Button(onClick = {
            state.targetState = !state.targetState
        }) {
            Text(text = state.getAnimationState().toString())
        }

    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedVisibilityTest3() {
    var visible by remember { mutableStateOf(true) }
    Column {
        AnimatedVisibility(visible = visible, enter = fadeIn(), exit = fadeOut()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(Color.DarkGray)
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .animateEnterExit(enter = slideInHorizontally { -it }, exit = slideOutHorizontally { -it })
                        .sizeIn(minWidth = 256.dp, minHeight = 64.dp)
                        .background(Color.Red)
                ) {
                    Text(
                        text = visible.toString(), modifier = Modifier
                            .align(Alignment.Center)
                    )
                }
            }
        }
        Button(onClick = { visible = !visible }) {
            Text(text = visible.toString())
        }
    }
}

/**
 *自定义的transition动画会随着AnimatedVisibility一起执行,
 *并且AnimatedVisibility会等transition执行完毕再移出屏幕
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
@Preview
fun AnimatedVisibilityTest4() {
    Column {
        var visible by remember { mutableStateOf(true) }
        AnimatedVisibility(visible = visible, enter = fadeIn(), exit = fadeOut()) {
            val background by transition.animateColor(transitionSpec = { tween(3000) }, label = "ColorAnimation") { state ->
                if (state == EnterExitState.Visible) Color.Blue else Color.Gray
            }
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(background)
            )
        }
        Button(onClick = { visible = !visible }) {
            Text(text = visible.toString())
        }
    }
}
