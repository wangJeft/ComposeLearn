package com.jeft.chatty.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.jeft.chatty.bean.UserProfileData
import com.jeft.chatty.ui.components.AppScreen
import com.jeft.chatty.ui.components.CenterRow
import com.jeft.chatty.ui.components.CircleShapeImage
import com.jeft.chatty.ui.components.NumberChips
import com.jeft.chatty.ui.components.WidthSpacer
import com.jeft.chatty.ui.theme.chattyColors
import com.jeft.chatty.ui.utils.LocalNavController
import kotlin.random.Random

@Composable
fun FriendMessageItem(
    userProfileData: UserProfileData,
    lastMag: String,
    unreadCount: Int = 0
) {
    val navController = LocalNavController.current
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate("${AppScreen.conversation}/${userProfileData.uid}")
            },
        color = MaterialTheme.chattyColors.backgroundColor
    ) {
        CenterRow(modifier = Modifier.padding(vertical = 8.dp, horizontal = 10.dp)) {
            CircleShapeImage(size = 60.dp, painter = painterResource(id = userProfileData.avatarRes))
            Spacer(modifier = Modifier.padding(horizontal = 10.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = userProfileData.nickname, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.chattyColors.textColor)
                Spacer(modifier = Modifier.padding(vertical = 3.dp))
                Text(text = lastMag, style = MaterialTheme.typography.bodyMedium, maxLines = 2, overflow = TextOverflow.Ellipsis, color = MaterialTheme.chattyColors.textColor)
            }
            WidthSpacer(value = 4.dp)
            val randomTime = remember {
                "${Random.nextInt(0, 24)}:${Random.nextInt(10, 60)}"
            }
            if (unreadCount > 0) {
                Box(contentAlignment = Alignment.CenterEnd) {
                    Column(horizontalAlignment = Alignment.End) {
                        Text(text = randomTime, color = MaterialTheme.chattyColors.textColor.copy(0.5f))
                        Spacer(modifier = Modifier.padding(vertical = 3.dp))
                        NumberChips(number = unreadCount)
                    }
                }
            }
        }
    }
}