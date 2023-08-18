package com.jeft.chatty.drawer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.jeft.chatty.R
import com.jeft.chatty.bean.UserProfileData
import com.jeft.chatty.home.mock.friends
import com.jeft.chatty.ui.components.HeightSpacer

@Composable
fun PersonalProfile() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .paint(painterResource(id = R.drawable.google_bg), contentScale = ContentScale.FillBounds)
        ) {
            PersonalProfileHeader()
        }
        HeightSpacer(value = 10.dp)
        PersonalProfileDetail()
        Box(modifier = Modifier.fillMaxHeight(), contentAlignment = Alignment.BottomCenter) {
            BottomSettingIcons()
        }
    }
}

fun getCurrentLoginUserProfile(): UserProfileData {
    return friends.random()
}

@Composable
fun BottomSettingIcons() {
    TODO("Not yet implemented")
}

@Composable
fun PersonalProfileDetail() {

}

@Composable
fun PersonalProfileHeader() {
    val currentUser = getCurrentLoginUserProfile()
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(10.dp)
    ) {
        val (portraitImageRef, usernameTextRef,desTextRef) = remember { createRefs() }
         
    }
}
