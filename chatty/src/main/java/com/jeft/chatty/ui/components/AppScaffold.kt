package com.jeft.chatty.ui.components

import android.annotation.SuppressLint
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
import com.jeft.chatty.screens.drawer.PersonalProfile
import com.jeft.chatty.screens.home.Home
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun AppScaffold() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    var selectedScreen by remember { mutableStateOf(0) }

    ModalNavigationDrawer(
        drawerContent = { PersonalProfile() },
        drawerState = drawerState,
        modifier = Modifier.navigationBarsPadding()
    ) {
        Scaffold(bottomBar = {
            MyBottomNavigationBar(
                selectedScreen = selectedScreen,
                onClick = { index ->
                    scope.launch {
                        pagerState.scrollToPage(index)
                    }
                })
        }) {
            HorizontalPager(
                pageCount = BottomScreen.values().size,
                state = pagerState,
                userScrollEnabled = false,
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