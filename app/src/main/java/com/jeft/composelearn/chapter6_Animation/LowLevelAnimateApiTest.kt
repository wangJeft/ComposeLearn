package com.jeft.composelearn.chapter6_Animation

import androidx.compose.animation.Animatable
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.ExperimentalTransitionApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.createChildTransition
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jeft.composelearn.R
import kotlinx.coroutines.launch

@Preview @Composable fun LowLevelAnimateApiTest() {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Text(text = "AnimateXXXAsState实现")
        AnimateXXXAsState()
        Text(text = "Animatable实现")
        AnimatableTest()
        Text(text = "TransitionTest")
        TransitionTest()
        Text(text = "Transition转换AnimatedVisibility,AnimatedContent")
        TransitionToAnimatedVisibilityAndContent()
        Text(text = "封装Transition")
        PackageTransition()
        /*Text(text = "InfiniteTransition")
        InfiniteTransition()*/
        Text(text = "springAnimation")
        SpringAnimationTest()
        Text(text = "KeyframesAnimTest")
        KeyframesAnimTest()
    }
}

//此系列为animate***AsState动画, Dp,Color,Float,Int,Offset,Rect,Size,Value,IntOffset,IntSize等等,
@Composable fun AnimateXXXAsState() {
    var change by remember {
        mutableStateOf(false)
    }
    var flag by remember {
        mutableStateOf(false)
    }
    val buttonSize by animateDpAsState(targetValue = if (change) 32.dp else 24.dp, animationSpec = tween(3000))
    val buttonColor by animateColorAsState(targetValue = if (flag) Color.Red else Color.Gray, animationSpec = tween(3000))
    if (buttonSize == 32.dp) {
        change = false
    }
    IconButton(onClick = {
        change = true
        flag = !flag
    }) {
        Icon(Icons.Rounded.Favorite, modifier = Modifier.size(buttonSize), tint = buttonColor, contentDescription = "l")
    }
}

@Composable fun AnimatableTest() {
    var change by remember {
        mutableStateOf(false)
    }
    var flag by remember {
        mutableStateOf(false)
    }
    val buttonSize = remember {
        Animatable(24.dp, Dp.VectorConverter)
    }
    val buttonColor = remember {
        Animatable(Color.Gray)
    }
    LaunchedEffect(change, flag) {
        buttonSize.animateTo(if (change) 32.dp else 24.dp)
        buttonColor.animateTo(if (flag) Color.Red else Color.Gray)
    }
    if (buttonSize.value == 32.dp) {
        change = false
    }
    IconButton(onClick = {
        change = true
        flag = !flag
    }) {
        Icon(Icons.Rounded.Favorite, modifier = Modifier.size(buttonSize.value), tint = buttonColor.value, contentDescription = "l")
    }
}

sealed class SwitchState {
    object OPEN : SwitchState()
    object CLOSE : SwitchState()
}

/**
 * Transition可以面向多个目标值应用动画,并保持它们同步结束
 */
@OptIn(ExperimentalTransitionApi::class) @Composable fun TransitionTest() {
    var selectedState: SwitchState by remember {
        mutableStateOf(SwitchState.CLOSE)
    }
    val transition = updateTransition(targetState = selectedState, label = "switch_transition")
    val selectBarPadding by transition.animateDp(transitionSpec = { tween(1000) }, label = "") {
        when (it) {
            SwitchState.CLOSE -> 40.dp
            SwitchState.OPEN -> 0.dp
        }
    }
    val textAlpha by transition.animateFloat(transitionSpec = { tween(1000) }, label = "") {
        when (it) {
            SwitchState.CLOSE -> 1f
            SwitchState.OPEN -> 0f
        }
    }
    Box(modifier = Modifier
        .size(150.dp)
        .padding(8.dp)
        .clip(RoundedCornerShape(10.dp))
        .clickable { selectedState = if (selectedState == SwitchState.OPEN) SwitchState.CLOSE else SwitchState.OPEN }) {
        Image(painter = painterResource(id = R.drawable.gugong), contentDescription = "", contentScale = ContentScale.FillBounds)
        Text(
            text = "click me", fontSize = 30.sp, fontWeight = FontWeight.W900, color = Color.White, modifier = Modifier
                .align(Alignment.Center)
                .alpha(textAlpha)
        )

        transition.createChildTransition { parentState ->
            val height = when (parentState) {
                SwitchState.CLOSE -> 0.dp
                SwitchState.OPEN -> 40.dp
            }
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .height(height)
                    .padding()
                    .background(Color(0xFF5FB878))
            ) {
                Row(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .alpha(1 - textAlpha)
                ) {
                    Icon(Icons.Filled.Star, contentDescription = "star", tint = Color.White)
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = "selected", fontSize = 20.sp, fontWeight = FontWeight.W900, color = Color.White
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(40.dp)
                .padding(top = selectBarPadding)
                .background(Color(0xFF5FB878))
        ) {


            Row(
                modifier = Modifier
                    .align(Alignment.Center)
                    .alpha(1 - textAlpha)
            ) {
                Icon(Icons.Filled.Star, contentDescription = "star", tint = Color.White)
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = "selected", fontSize = 20.sp, fontWeight = FontWeight.W900, color = Color.White
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class) @Composable fun TransitionToAnimatedVisibilityAndContent() {
    var selected by remember {
        mutableStateOf(false)
    }
    val transition = updateTransition(selected, label = "TransitionToAnimatedVisibilityAndContent")
    val borderColor by transition.animateColor(label = "borderColor") { isSelected ->
        if (isSelected) Color.Magenta else Color.White
    }
    val elevation by transition.animateDp(label = "elevation") { isSelected ->
        if (isSelected) 10.dp else 2.dp
    }
    Surface(
        onClick = { selected = !selected }, shape = RoundedCornerShape(8.dp), border = BorderStroke(2.dp, borderColor), elevation = elevation
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Hello world!")
            //transition的状态值传入作为判断条件
            transition.AnimatedVisibility(
                visible = { targetSelected -> targetSelected }, enter = expandVertically(), exit = shrinkVertically()
            ) {
                Text(text = "it is fine today")
            }
            //transition的状态值传入作为判断条件
            transition.AnimatedContent { targetState ->
                if (targetState) {
                    Text(text = "Selected")
                } else {
                    Icon(imageVector = Icons.Default.Phone, contentDescription = "")
                }
            }

        }
    }
}

//Transition动画的封装
enum class BoxState { Collapsed, Expanded }


@Composable fun PackageTransition() {
    var collapsed by remember {
        mutableStateOf(BoxState.Collapsed)
    }
    Column {
        Button(onClick = {
            collapsed = if (collapsed == BoxState.Collapsed) {
                BoxState.Expanded
            } else {
                BoxState.Collapsed
            }
        }) {
            Text(text = collapsed.toString())
        }
        AnimatingBox(boxState = collapsed)
    }
}

@Composable fun AnimatingBox(boxState: BoxState) {
    val transitionData = updateTransitionData(boxState = boxState)
    Box(
        modifier = Modifier
            .background(transitionData.color)
            .size(transitionData.size)
    ) {

    }
}

private class TransitionData(color: State<Color>, size: State<Dp>) {
    val color by color
    val size by size
}

@Composable private fun updateTransitionData(boxState: BoxState): TransitionData {
    val transition = updateTransition(targetState = boxState, "boxTransition")
    val color = transition.animateColor(label = "color", transitionSpec = { tween(1500) }) { state ->
        when (state) {
            BoxState.Collapsed -> Color.Gray
            BoxState.Expanded -> Color.Red
        }
    }
    val size = transition.animateDp(label = "", transitionSpec = { tween(1500) }) { state ->
        when (state) {
            BoxState.Collapsed -> 64.dp
            BoxState.Expanded -> 128.dp
        }
    }
    return remember(transition) {
        TransitionData(color, size)
    }
}

@Composable fun InfiniteTransition() {
    val infiniteTransition = rememberInfiniteTransition()
    val color by infiniteTransition.animateColor(
        initialValue = Color.Magenta, targetValue = Color.Black, animationSpec = infiniteRepeatable(animation = tween(1500, easing = FastOutSlowInEasing), repeatMode = RepeatMode.Reverse)
    )
    val size by infiniteTransition.animateFloat(
        initialValue = 30.dp.value, targetValue = 60.dp.value, animationSpec = infiniteRepeatable(animation = tween(1500, easing = FastOutSlowInEasing), repeatMode = RepeatMode.Reverse)
    )
    Box(
        modifier = Modifier
            .background(color)
            .size(size.dp)
    )
}

@Composable fun SpringAnimationTest() {
    val spacerHeight = 10.dp
    var targetValue by remember { mutableStateOf(0f) }
    val offset = remember {
        listOf(
            Animatable(0f) to spring<Float>(
                dampingRatio = Spring.DampingRatioHighBouncy, stiffness = Spring.StiffnessVeryLow
            ),
            Animatable(0f) to spring(
                dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessVeryLow
            ),
            Animatable(0f) to spring(
                dampingRatio = Spring.DampingRatioLowBouncy, stiffness = Spring.StiffnessVeryLow
            ),
            Animatable(0f) to spring(
                dampingRatio = Spring.DampingRatioNoBouncy, stiffness = Spring.StiffnessVeryLow
            ),
            Animatable(0f) to spring(
                dampingRatio = 2f, stiffness = Spring.StiffnessVeryLow
            ),
        )
    }

    LaunchedEffect(targetValue) {
        if (targetValue == 0f) {
            offset.forEach {
                launch { it.first.snapTo(targetValue) }

            }
        } else {
            offset.forEach {
                launch { it.first.animateTo(targetValue, it.second) }
            }
        }
    }

    Column {
        /*val infiniteTransition = rememberInfiniteTransition()
        val offsetX by infiniteTransition.animateFloat(
            initialValue = 0f, targetValue = 1f,
            animationSpec = infiniteRepeatable(animation = tween(1500, easing = FastOutSlowInEasing), repeatMode = RepeatMode.Reverse)
        )*/
        SpringDemoItem(name = "DampingRatioHighBouncy(0.2f)", offset[0].first.value)
        Spacer(modifier = Modifier.height(spacerHeight))
        SpringDemoItem(name = "DampingRatioMediumBouncy(0.5f)", offset[1].first.value)
        Spacer(modifier = Modifier.height(spacerHeight))
        SpringDemoItem(name = "DampingRatioLowBouncy(0.75f)", offset[2].first.value)
        Spacer(modifier = Modifier.height(spacerHeight))
        SpringDemoItem(name = "DampingRatioNoBouncy(1f)", offset[3].first.value)
        Spacer(modifier = Modifier.height(spacerHeight))
        SpringDemoItem(name = "dampingRatio = 2f", offset[4].first.value)
        Spacer(modifier = Modifier.height(spacerHeight))
        Button(onClick = {
            targetValue = (1f - targetValue)
        }) {
            Text(text = "Start animations")
        }
    }
}

@Composable fun SpringDemoItem(name: String, offsetX: Float) {
    Column {
        Text(name)

        Spacer(modifier = Modifier.height(5.dp))

        Box(modifier = Modifier.fillMaxWidth()) {

            Spacer(
                modifier = Modifier
                    .width(200.dp)
                    .height(15.dp)
                    .background(Color.Gray)
            )

            Box(modifier = Modifier.offset((offsetX * 185).dp)) {
                Spacer(
                    modifier = Modifier
                        .size(15.dp)
                        .background(Color.Red)
                )
            }

        }

    }
}

@Composable fun KeyframesAnimTest() {
    var clickeable by remember {
        mutableStateOf(true)
    }
    val value by animateDpAsState(targetValue = if (clickeable) 50.dp else 200.dp, animationSpec = keyframes {
        durationMillis = 5000
        50.dp at 1000 with LinearOutSlowInEasing
        100.dp at 2000 with FastOutSlowInEasing
        150.dp at 3000
        200.dp at 4000
    })
    clickeable = false
    Box(
        modifier = Modifier
            .background(Color.Magenta)
            .height(15.dp)
            .width(value)
            .clickable {
                clickeable = !clickeable
            }
    )
}