package com.jeft.composelearn.chapter5_draw

import android.graphics.Bitmap
import androidx.annotation.FloatRange
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageShader
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.core.graphics.scale
import androidx.core.graphics.transform
import com.jeft.composelearn.R
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt
import kotlin.math.sin

private const val defaultAmplitude = 0.2f
private const val defaultVelocity = 1.0f
private const val waveDuration = 2000

data class WaveConfig(
    @FloatRange(from = 0.0, to = 1.0) val progress: Float = 0f,
    @FloatRange(from = 0.0, to = 1.0) val amplitude: Float = defaultAmplitude,
    @FloatRange(from = 0.0, to = 1.0) val velocity: Float = defaultVelocity
)

@Composable
fun WaveLoadingDemo() {
    var progress by remember { mutableStateOf(0.5f) }
    var velocity by remember { mutableStateOf(1.0f) }
    var amplitude by remember { mutableStateOf(0.2f) }
    val size = LocalDensity.current.run { 200.dp.toPx().roundToInt() }
    val bitmap = ImageBitmap.imageResource(id = R.drawable.avatar).asAndroidBitmap().scale(size,size)

    Column {
        Box(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterHorizontally)
        ) {
            WaveLoading(
                modifier = Modifier
                    .size(200.dp, 200.dp)
                    .clipToBounds()
                    .align(Alignment.Center),
                waveConfig = WaveConfig(progress, amplitude, velocity),
                bitmap = bitmap
            )
        }
        LabelSlider(
            label = "Progress",
            value = progress,
            onValueChange = { progress = it },
            range = 0f..1f
        )

        LabelSlider(
            label = "Velocity",
            value = velocity,
            onValueChange = { velocity = it },
            range = 0f..1f
        )

        LabelSlider(
            label = "Amplitude",
            value = amplitude,
            onValueChange = { amplitude = it },
            range = 0f..1f
        )

    }
}

@Composable
fun LabelSlider(
    label: String,
    value: Float,
    onValueChange: (Float) -> Unit,
    range: ClosedFloatingPointRange<Float>
) {
    Row(modifier = Modifier.padding(start = 10.dp, end = 10.dp)) {
        Text(
            text = label, modifier = Modifier
                .width(100.dp)
                .align(Alignment.CenterVertically)
        )
        Slider(
            modifier = Modifier.align(Alignment.CenterVertically),
            value = value,
            onValueChange = onValueChange,
            valueRange = range
        )
    }
}


@Composable
fun WaveLoading(modifier: Modifier, waveConfig: WaveConfig, bitmap: Bitmap) {
    val transition = rememberInfiniteTransition()
    val animates = listOf(1.0f, 0.75f, 0.5f).map {
        transition.animateFloat(
            initialValue = 0f, targetValue = 1f, animationSpec = InfiniteRepeatableSpec(
                animation = tween((it * waveDuration).roundToInt()), repeatMode = RepeatMode.Reverse
            )
        )
    }
    Canvas(modifier.fillMaxSize()) {
        drawWave(bitmap.asImageBitmap(), waveConfig, animates)
    }
}

private fun DrawScope.drawWave(
    imageBitmap: ImageBitmap, waveConfig: WaveConfig, animates: List<State<Float>>
) {
    drawImage(image = imageBitmap, colorFilter = run {
        val cm = ColorMatrix().apply { setToSaturation(0f) }
        ColorFilter.colorMatrix(cm)
    })
    animates.forEachIndexed { index, anim ->
        val maxWidth = 2 * size.width / waveConfig.velocity.coerceAtLeast(0.1f)
        val offsetX = maxWidth / 2 * (1 - anim.value)
        translate(-offsetX) {
            drawPath(
                path = buildWavePath(
                    width = maxWidth,
                    height = size.height,
                    amplitude = size.height * waveConfig.amplitude,
                    progress = waveConfig.progress
                ), brush = ShaderBrush(ImageShader(imageBitmap).apply {
                    transform { postTranslate(offsetX, 0f) }
                }), alpha = if (index == 0) 1f else 0.5f
            )
        }
    }

}

fun buildWavePath(
    dp: Float = 3f, width: Float, height: Float, amplitude: Float, progress: Float
): Path {
    //调整振幅, 振幅不大于剩余的空间
    val adjustHeight = min(height * max(0f, 1 - progress), amplitude)
    return Path().apply {
        reset()
        moveTo(0f, height)
        lineTo(0f, height * (1 - progress))
        if (progress > 0f && progress < 1f) {
            if (adjustHeight > 0) {
                var x = dp
                while (x < width) {
                    lineTo(
                        x,
                        height * (1 - progress) - adjustHeight / 2f * sin(4.0 * Math.PI * x / width).toFloat()
                    )
                    x += dp
                }
            }
        }
        lineTo(width, height * (1 - progress))
        lineTo(width, height)
        close()
    }
}
