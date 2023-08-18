package com.jeft.chatty.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jeft.chatty.R


@Composable
fun MyBottomNavigationBar(
    selectedScreen: Int,
    onClick: (targetIndex: Int) -> Unit
) {
    NavigationBar(modifier = Modifier.navigationBarsPadding()) {
        BottomScreen.values().forEachIndexed { index, bottomScreen ->
            NavigationBarItem(selected = selectedScreen == index,
                onClick = { onClick(index) },
                icon = {
                    Icon(
                        imageVector = if (selectedScreen == index) bottomScreen.selectedIcon else bottomScreen.unselectedIcon,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = { Text(text = stringResource(id = bottomScreen.label)) }
            )
        }
    }
}

enum class BottomScreen(
    @StringRes
    val label: Int,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector
) {
    Message(R.string.message, Icons.Outlined.Chat, Icons.Filled.Chat),
    Contract(R.string.contract, Icons.Outlined.Group, Icons.Filled.Group),
    Explore(R.string.explore, Icons.Outlined.Explore, Icons.Filled.Explore)
}

object AppScreen {
    const val main = "main"
    const val register = "register"
}