package com.jeft.composelearn.chapter10_game_tetris

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke

private fun DrawScope.drawBrick(brickSize: Float, offset: Offset, color: Color) {
    //根据offset计算实际位置和size
    val actualLocation = Offset(offset.x * brickSize, offset.y * brickSize)
    val outerSize = brickSize * 0.8f
    val outerOffset = (brickSize - outerSize) / 2
    drawRect(color, topLeft = actualLocation + Offset(outerOffset, outerOffset), size = Size(outerSize, outerSize), style = Stroke(outerSize / 10))
    val innerSize = brickSize * 0.5f
    val innerOffset = (brickSize - innerSize) / 2
    drawRect(color, actualLocation + Offset(innerOffset, innerOffset), size = Size(innerSize, innerSize))
}

private fun DrawScope.drawMatrix(brickSize: Float, matrix: Pair<Int, Int>) {
    (0 until matrix.first).forEach { x ->
        (0 until matrix.second).forEach { y ->
            drawBrick(brickSize, Offset(x.toFloat(), y.toFloat()), Color.Gray)
        }
    }
}