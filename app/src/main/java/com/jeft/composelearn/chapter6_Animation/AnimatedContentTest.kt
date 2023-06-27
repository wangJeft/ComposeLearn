package com.jeft.composelearn.chapter6_Animation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun AnimatedContentTest() {
    Column {
        AnimatedContent1()
        Text(text = "设置自定义的transitionSpec")
        AnimatedContent2()
        Text(text = "SizeTransform")
        AnimatedContent3()
        AnimateContentSizeTest()
    }
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedContent1() {
    Column {
        var count by remember {
            mutableStateOf(0)
        }
        AnimatedContent(targetState = count) { targetCount ->
            Text(text = "Count: $targetCount")
        }
        Button(onClick = { count++ }) {
            Text(text = "Add")
        }
    }
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedContent2() {
    Column {
        var count by remember {
            mutableStateOf(0)
        }
        AnimatedContent(targetState = count, transitionSpec = { slideInHorizontally { -it } + fadeIn() with slideOutHorizontally { it } + fadeOut() }) { targetCount ->
            Text(text = "Count: $targetCount")
        }
        Button(onClick = { count++ }) {
            Text(text = "Add")
        }
    }
}

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
@Composable
fun AnimatedContent3() {
    Column {
        var expanded by remember {
            mutableStateOf(false)
        }
        Surface(color = MaterialTheme.colors.primary, onClick = { expanded = !expanded }) {
            AnimatedContent(targetState = expanded, transitionSpec = {
                fadeIn(
                    animationSpec =
                    tween(1500, 150)
                ) with fadeOut(animationSpec = tween(1500)) using SizeTransform { initialSize, targetSize ->
                    if (targetState) {
                        keyframes {
                            //展开时,先水平方向展开
                            IntSize(targetSize.width, initialSize.height) at 1500
                            durationMillis = 3000
                        }
                    } else {
                        keyframes {
                            //收起时,先垂直方向收起
                            IntSize(initialSize.width, targetSize.height) at 1500
                            durationMillis = 3000
                        }
                    }
                }
            }) { targetExpanded ->
                if (targetExpanded) {
                    Text(
                        text = "contentDescriptioncontentDescriptioncontentDescriptioncontentDescriptioncontentDescrip" +
                                "tioncontentDescriptioncontentDescriptioncontentDescriptioncontentDescriptioncontentDescription"
                    )
                } else {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "")
                }
            }
        }
    }
}

@Composable//淡入淡出
fun AnimatedContentForCrossfade() {
    Column {
        var currentPage by remember {
            mutableStateOf(true)
        }
        Button(onClick = { currentPage = !currentPage }) {
            Text(text = "change")
        }
        Crossfade(targetState = currentPage, animationSpec = tween(durationMillis = 2000)) { screen ->
            when (screen) {
                true -> AnimatedContentTest()
                false -> AnimatedVisibilityTest()
            }
        }
    }
}

@Composable
fun AnimateContentSizeTest() {
    var expend by remember {
        mutableStateOf(false)
    }
    Column(Modifier.padding(16.dp)) {
        Text(text = "AnimatedContentSizeDemo")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { expend = !expend }) {
            Text(text = if (expend) "Shrink" else "Expand")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .background(Color.LightGray)
                .animateContentSize()
        ) {
            Text(
                text = "AnimateContentSizeTest,AnimateContentSizeTest,AnimateContentSizeTest,AnimateContentSizeTest," +
                        "AnimateContentSizeTest,AnimateContentSizeTest,AnimateContentSizeTest,AnimateContentSizeTest," +
                        "AnimateContentSizeTest,AnimateContentSizeTest,AnimateContentSizeTest,AnimateContentSizeTest,",
                fontSize = 16.sp,
                textAlign = TextAlign.Justify,
                modifier = Modifier.padding(16.dp),
                maxLines = if (expend) Int.MAX_VALUE else 2
            )
        }
    }
}