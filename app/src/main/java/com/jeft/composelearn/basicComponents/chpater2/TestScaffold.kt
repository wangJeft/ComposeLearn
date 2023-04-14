package com.jeft.composelearn.basicComponents.chpater2

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.jeft.composelearn.R


data class Item(val name: String, val icon: Int)

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TestScaffold() {

    var selectedItem by remember {
        mutableStateOf(0)
    }
    val items = listOf<Item>(
        Item("主页", R.drawable.home),
        Item("列表", R.drawable.list),
        Item("设置", R.drawable.setting)
    )

    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "主页") }, navigationIcon = {
            IconButton(
                onClick = { },
            ) {
                Icon(Icons.Filled.Menu, contentDescription = "")
            }
        })
    }) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "主页界面")

        }

    }
}