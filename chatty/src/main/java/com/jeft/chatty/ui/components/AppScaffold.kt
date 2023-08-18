package com.jeft.chatty.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import com.jeft.chatty.home.Home
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun AppScaffold() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    var selectedScreen by remember { mutableStateOf(0) }
    ModalNavigationDrawer(
        drawerContent = { /*TODO*/ },
        drawerState = drawerState,
        modifier = Modifier.navigationBarsPadding()
    ) {
        Scaffold(bottomBar = {
            MyBottomNavigationBar(selectedScreen = selectedScreen, onClick = { index ->
                scope.launch {
                    pagerState.scrollToPage(index)
                }
            })
        }) { innerPadding ->
            HorizontalPager(
                pageCount = BottomScreen.values().size,
                state = pagerState,
                userScrollEnabled = false,
                contentPadding = innerPadding
            ) { page: Int ->
                when (BottomScreen.values()[page]) {
                    BottomScreen.Message -> Home(drawerState = drawerState)
                    BottomScreen.Contract -> {
                        // TODO:  
                    }

                    BottomScreen.Explore -> {
                        // TODO:
                    }
                }
            }
        }
    }
    LaunchedEffect(key1 = pagerState, block = {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            selectedScreen = page
        }
    })
    BackHandler(drawerState.isOpen) {
        scope.launch {
            drawerState.close()
        }
    }
}