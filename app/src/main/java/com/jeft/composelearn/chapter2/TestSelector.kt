package com.jeft.composelearn.chapter2

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.dp

@Composable
fun TestSelector() {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        val checkedState = remember {
            mutableStateOf(true)
        }
        Checkbox(
            checked = checkedState.value,
            onCheckedChange = { checkedState.value = it },
            colors = CheckboxDefaults.colors(checkedColor = Color(0xFF0079D3))
        )

        val (state, onStateChange) = remember {
            mutableStateOf(true)
        }
        val (state2, onStateChange2) = remember {
            mutableStateOf(true)
        }
        val parentState = remember(state, state2) {
            if (state && state2) ToggleableState.On
            else if (!state && !state2) ToggleableState.Off
            else ToggleableState.Indeterminate
        }
        val onParentClick = {
            val s = parentState != ToggleableState.On
            onStateChange(s)
            onStateChange2(s)
        }
        TriStateCheckbox(
            state = parentState,
            onClick = onParentClick,
            colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colors.primary)
        )
        Row(modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 10.dp)) {
            Checkbox(checked = state, onCheckedChange = onStateChange)
            Checkbox(checked = state2, onCheckedChange = onStateChange2)

        }

        val switchCheckedState = remember {
            mutableStateOf(true)
        }
        Switch(
            checked = switchCheckedState.value,
            onCheckedChange = { switchCheckedState.value = it },
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.Cyan, checkedTrackColor = Color.Gray
            )
        )

        var sliderPosition by remember {
            mutableStateOf(0f)
        }
        Text(
            text = "%.1f".format(sliderPosition * 100) + "%",
            modifier = Modifier.align(alignment = CenterHorizontally)
        )
        Slider(value = sliderPosition, onValueChange = { sliderPosition = it })

    }

}