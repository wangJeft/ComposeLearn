package com.jeft.composelearn.chapter2

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jeft.composelearn.R

@Composable
fun TestButton() {
    Column {
        Button(onClick = {}, contentPadding = PaddingValues(2.dp)) {
            Text(text = "确认")
        }

        Button(
            onClick = {},
            contentPadding = PaddingValues(5.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Cyan, contentColor = Color.Gray
            )
        ) {
            Icon(
                imageVector = Icons.Filled.Done,
                contentDescription = "",
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
            Text(text = "确认")
        }

        val interactionSource = remember {
            MutableInteractionSource()
        }
        val isPressedAsState = interactionSource.collectIsPressedAsState()
        val borderColor = if (isPressedAsState.value) Color.Green else Color.Cyan

        Button(
            onClick = {},
            contentPadding = PaddingValues(horizontal = 12.dp),
            border = BorderStroke(2.dp, borderColor),
            interactionSource = interactionSource
        ) {
            Text(text = "long press")
        }

        IconButton(onClick = {}) {
            Icon(
                painter = painterResource(id = R.drawable.collect_24_x_24),
                contentDescription = "",
                tint = Color.Unspecified
            )
        }

        FloatingActionButton(onClick = {}) {
            Icon(
                Icons.Filled.KeyboardArrowUp, contentDescription = ""
            )
        }

        ExtendedFloatingActionButton(icon = {
            Icon(
                Icons.Filled.Favorite, contentDescription = "", tint = Color.Unspecified
            )
        }, text = { Text(text = "add to my favorite") }, onClick = {})

    }
}