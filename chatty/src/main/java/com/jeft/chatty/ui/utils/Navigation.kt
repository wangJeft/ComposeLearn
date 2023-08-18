package com.jeft.chatty.ui.utils

import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder

val LocalNavController = compositionLocalOf<NavHostController> {
    error("CompositionLocal LocalNavController not present")
}

fun NavOptionsBuilder.popUpAllBackStackEntry(navController: NavController) {
    navController.backQueue.reversed().forEach {
        it.destination.route?.let { route ->
            this.popUpTo(route) { inclusive = true }
        }
    }
}