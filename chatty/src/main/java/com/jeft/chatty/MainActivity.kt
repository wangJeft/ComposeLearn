package com.jeft.chatty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jeft.chatty.screens.conversation.ConversationScreen
import com.jeft.chatty.screens.conversation.ConversationUiState
import com.jeft.chatty.screens.conversation.LocalBackPressedDispatcher
import com.jeft.chatty.screens.conversation.mock.initialMessages
import com.jeft.chatty.screens.register.Register
import com.jeft.chatty.ui.components.AppScaffold
import com.jeft.chatty.ui.components.AppScreen
import com.jeft.chatty.ui.theme.ChattyTheme
import com.jeft.chatty.ui.theme.chattyColors
import com.jeft.chatty.ui.utils.LocalNavController
import com.jeft.chatty.ui.utils.hideIME

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            ChattyTheme {
                val systemUiController = rememberSystemUiController()
                val userDarkIcons = !isSystemInDarkTheme() && MaterialTheme.chattyColors.isLight
                SideEffect {
                    systemUiController.setSystemBarsColor(Color.Transparent, userDarkIcons)
                }

                val navController = rememberAnimatedNavController()

                DisposableEffect(Unit) {
                    val destinationChangedListener = NavController.OnDestinationChangedListener { _, _, _ ->
                        hideIME()
                    }
                    onDispose {
                        navController.removeOnDestinationChangedListener(destinationChangedListener)
                    }
                }

                CompositionLocalProvider(
                    LocalNavController provides navController,
                    LocalBackPressedDispatcher provides onBackPressedDispatcher
                ) {
                    ChattyNavHost(navController = navController)
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ChattyNavHost(navController: NavHostController) {
    val transSpec = remember { tween<IntOffset>(400) }
    AnimatedNavHost(navController = navController, startDestination = AppScreen.main) {
        composable(AppScreen.main) {
            AppScaffold()
        }
        composable(AppScreen.register) {
            Register()
        }
        composable(
            route = "${AppScreen.conversation}/{uid}",
            arguments = listOf(navArgument("uid") { type = NavType.StringType })
        ) { backStackEntry ->
            val uid = backStackEntry.arguments?.getString("uid")!!
            ConversationScreen(
                uiState = ConversationUiState(initialMessage = initialMessages, conversationUserId = uid)
            )
        }
    }
}
