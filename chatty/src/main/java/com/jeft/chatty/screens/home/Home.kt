package com.jeft.chatty.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jeft.chatty.screens.home.mock.displayMessages
import com.jeft.chatty.ui.theme.chattyColors

@Composable
fun Home(drawerState: DrawerState) {
    Scaffold(topBar = {
        HomeTopBar(drawerState = drawerState)
    }) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(MaterialTheme.chattyColors.backgroundColor)
        ) {
            itemsIndexed(displayMessages, key = { _, item ->
                item.mid
            }) { _, item ->
                FriendMessageItem(userProfileData = item.userProfile, lastMag = item.lastMsg, item.unreadCount)
            }
        }
    }
}