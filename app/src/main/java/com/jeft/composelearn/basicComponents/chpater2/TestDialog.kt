package com.jeft.composelearn.basicComponents.chpater2

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun TestDialog() {
    Column {
        val openDialog = remember {
            mutableStateOf(false)
        }

        Button(onClick = {
            openDialog.value = !openDialog.value
        }) {
            Text(
                text = if (openDialog.value) "CloseDialog" else {
                    "OpenDialog"
                }
            )
        }

        if (openDialog.value) {
            Dialog(
                onDismissRequest = { openDialog.value = false }, properties = DialogProperties(
                    dismissOnBackPress = true, dismissOnClickOutside = true
                )
            ) {
                Box(
                    modifier = Modifier
                        .size(300.dp, 400.dp)
                        .background(Color.White)
                ) {
                    Button(onClick = {
                        openDialog.value = false
                    }, modifier = Modifier.align(Alignment.Center)) {
                        Text(text = "Close")
                    }
                }
            }
        }


        val alertDialog = remember {
            mutableStateOf(false)
        }

        Button(onClick = {
            alertDialog.value = !alertDialog.value
        }) {
            Text(
                text = if (alertDialog.value) "CloseAlertDialog" else {
                    "OpenAlertDialog"
                }
            )
        }

        if (alertDialog.value) {
            AlertDialog(onDismissRequest = { alertDialog.value = false },
                title = { Text(text = "开启位置服务") },
                text = {
                    Text(text = "提供精确的位置使用,请务必打开")
                },
                confirmButton = {
                    Button(onClick = { alertDialog.value = false }) {
                        Text(text = "同意")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { alertDialog.value = false }) {
                        Text(text = "取消")
                    }
                })
        }
    }
}