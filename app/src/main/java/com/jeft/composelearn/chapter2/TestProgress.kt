package com.jeft.composelearn.chapter2

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.ProgressIndicatorDefaults.IndicatorBackgroundOpacity
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TestProgress() {
    var progress by remember {
        mutableStateOf(0.1f)
    }

    val animatedProgress by animateFloatAsState(
        targetValue = progress, animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    )
    Column(
        modifier = Modifier
            .padding(10.dp)
            .border(2.dp, color = MaterialTheme.colors.primary),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            progress = animatedProgress,
            backgroundColor = MaterialTheme.colors.primary.copy(alpha = IndicatorBackgroundOpacity),
        )
        Spacer(modifier = Modifier.height(30.dp))
        LinearProgressIndicator(progress = animatedProgress)
        Spacer(modifier = Modifier.height(30.dp))
        Row {

            OutlinedButton(onClick = {
                if (progress >= 1.0f) {
                    return@OutlinedButton
                }
                progress += 0.1f
            }) {
                Text(text = "增加进度")
            }
            Spacer(modifier = Modifier.width(30.dp))
            OutlinedButton(onClick = {
                if (progress == 0f) {
                    return@OutlinedButton
                }
                progress -= 0.1f
            }) {
                Text(text = "减少进度")
            }
        }
    }
}