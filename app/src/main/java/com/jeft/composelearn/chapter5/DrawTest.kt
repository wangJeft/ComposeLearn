package com.jeft.composelearn.chapter5

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jeft.composelearn.R

@Preview
@Composable
fun LoadingProgressBar() {
    var sweepAngle by remember {
        mutableStateOf(180F)
    }
    Box(modifier = Modifier.size(375.dp), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Loading",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp)
        ) {
            drawCircle(
                color = Color(0xFF1E7171),
                center = Offset(drawContext.size.width / 2f, drawContext.size.height / 2f),
                style = Stroke(width = 20.dp.toPx())
            )
            drawArc(
                color = Color(0xFF00BCD4),
                startAngle = -90f,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = 20.dp.toPx(), cap = StrokeCap.Round)
            )
        }
    }
}

//drawWithContent用来绘制组件的内容
@Preview
@Composable
fun DrawBefore() {
    Box(modifier = Modifier.size(120.dp), contentAlignment = Alignment.Center) {
        Card(shape = RoundedCornerShape(8.dp), modifier = Modifier
            .size(100.dp)
            .drawWithContent {
                drawContent()
                drawCircle(
                    Color(0xFFE7614E),
                    18.dp.toPx() / 2,
                    center = Offset(drawContext.size.width, 0f)
                )
            }) {
            Image(painter = painterResource(id = R.drawable.avatar), contentDescription = "")
        }
    }
}

//drawBehind会再组件的 内容绘制 之前先绘制, 所以一般用来绘制组件的背景
@Preview
@Composable
fun DrawBehind() {
    Box(modifier = Modifier.size(120.dp), contentAlignment = Alignment.Center) {
        Card(shape = RoundedCornerShape(8.dp), modifier = Modifier
            .size(100.dp)
            .drawBehind {
                drawCircle(
                    Color(0xFFE7614E),
                    18.dp.toPx() / 2,
                    center = Offset(drawContext.size.width, 0f)
                )
            }) {
            Image(painter = painterResource(id = R.drawable.avatar), contentDescription = "")
        }
    }
}

//绘制的过程中需要创建对象时, 使用drawWithCache, 可以避免重复的创建对象引起的内存抖动
//如果再drawWithContent或drawBehind方法中依赖了某个可变状态,当该状态更新时,会导致当前组件重新进行绘制阶段,也被称作重绘
@Composable
fun DrawCache() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        var transition = rememberInfiniteTransition()
        val alpha by transition.animateFloat(
            initialValue = 0f, targetValue = 1f, animationSpec = infiniteRepeatable(
                animation = tween(2000, easing = LinearEasing), repeatMode = RepeatMode.Reverse
            )
        )
        var context = LocalContext.current
        Box(modifier = Modifier
            .size(340.dp)
            .drawWithCache {
                val image = ImageBitmap.imageResource(context.resources, R.drawable.img)
                onDrawBehind {
                    drawImage(
                        image = image,
                        dstOffset = IntOffset.Zero,
                        alpha = alpha
                    )
                }
            })
    }
}