package com.jeft.composelearn.basicComponents.chapter2

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.jeft.composelearn.R
import kotlinx.coroutines.launch


data class Item(val name: String, val icon: Int)

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TestScaffold() {

    val selectedItem by remember {
        mutableStateOf(0)
    }
    val items = listOf<Item>(
        Item("主页", R.drawable.home),
        Item("列表", R.drawable.list),
        Item("设置", R.drawable.setting)
    )

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "主页") }, navigationIcon = {
            IconButton(
                onClick = {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                },
            ) {
                Icon(Icons.Filled.Menu, contentDescription = "")
            }
        })
    }, bottomBar = {
        BottomNavigation {
            items.forEachIndexed { index, item ->
                BottomNavigationItem(selected = selectedItem == index, onClick = { }, icon = {
                    Icon(
                        painter = painterResource(id = item.icon), contentDescription = item.name
                    )
                }, alwaysShowLabel = false, label = { Text(text = item.name) })
            }
        }
    }, drawerContent = { Text(text = "侧边栏") }, scaffoldState = scaffoldState) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "主页界面")
        }

    }
    BackHandler(enabled = scaffoldState.drawerState.isOpen) {
        scope.launch {
            scaffoldState.drawerState.close()
        }
    }
}