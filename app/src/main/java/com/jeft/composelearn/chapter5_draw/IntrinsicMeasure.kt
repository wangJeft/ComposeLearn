package com.jeft.composelearn.chapter5_draw

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.IntrinsicMeasurable
import androidx.compose.ui.layout.IntrinsicMeasureScope
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun IntrinsicsMeasure() {
    TwoText(text1 = "start", text2 = "end")

}

@Composable
fun TwoText(text1: String, text2: String) {
    Column {

        Row(modifier = Modifier.height(IntrinsicSize.Min)) {
            Text(
                text = text1,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 4.dp)
                    .wrapContentWidth(Alignment.Start)
            )
            Divider(
                color = Color.Black, modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
            )
            Text(
                text = text2,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 4.dp)
                    .wrapContentWidth(Alignment.End)
            )
        }
        IntrinsicRow(modifier = Modifier.height(IntrinsicSize.Min)) {
            Text(
                text = text1,
                modifier = Modifier
                    .padding(start = 4.dp)
                    .wrapContentWidth(Alignment.Start)
                    .layoutId("main")
            )
            Divider(
                color = Color.Black, modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
                    .layoutId("divider")
            )
            Text(
                text = text2,
                modifier = Modifier
                    .wrapContentWidth(Alignment.End)
                    .layoutId("main")
            )
        }

    }
}

@Composable
fun IntrinsicRow(modifier: Modifier, content: @Composable () -> Unit) {
    Layout(content = content, modifier = modifier, measurePolicy = object : MeasurePolicy {
        override fun MeasureScope.measure(
            measurables: List<Measurable>, constraints: Constraints
        ): MeasureResult {
            var devideConstraints = constraints.copy(minWidth = 0)
            var mainPlaceables =
                measurables.filter { it.layoutId == "main" }.map { it.measure(constraints) }
            var devidePlaceable =
                measurables.first { it.layoutId == "divider" }.measure(devideConstraints)
            var midPos = constraints.maxWidth / 2
            return layout(constraints.maxWidth, constraints.maxHeight) {
                mainPlaceables.forEach {
                    it.placeRelative(0, 0)
                }
                devidePlaceable.placeRelative(midPos, 0)
            }
        }


        override fun IntrinsicMeasureScope.minIntrinsicHeight(
            measurables: List<IntrinsicMeasurable>, width: Int
        ): Int {
            var maxHeight = 0
            measurables.forEach {
                maxHeight = it.maxIntrinsicHeight(width).coerceAtLeast(maxHeight)
            }
            return maxHeight
        }

    })
}