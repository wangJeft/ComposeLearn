package com.jeft.chatty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jeft.chatty.register.Register
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

                CompositionLocalProvider(LocalNavController provides navController) {
                    AnimatedNavHost(navController = navController, startDestination = AppScreen.main) {
                        composable(AppScreen.main){
                            AppScaffold()
                        }
                        composable(AppScreen.register) {
                            Register()
                        }
                    }
                }
            }
        }
    }
}

