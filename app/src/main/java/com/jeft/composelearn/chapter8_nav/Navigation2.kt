package com.jeft.composelearn.chapter8_nav

import android.app.FragmentManager.BackStackEntry
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

sealed class Screen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : Screen("home", "home", Icons.Filled.Home)
    object Favorite : Screen("favorite", "favorite", Icons.Filled.Favorite)
    object Notification : Screen("notification", "notification", Icons.Filled.Notifications)
    object Cart : Screen("cart", "cart", Icons.Filled.ShoppingCart)
}

val items = listOf(Screen.Home, Screen.Favorite, Screen.Notification, Screen.Cart)

val uri = "android-app://compose.learn"

@Preview
@Composable
fun Navigation2() {
    val navController = rememberNavController()
    Scaffold(bottomBar = {
        BottomNavigation {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            items.forEach { screen ->
                BottomNavigationItem(selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                    onClick = {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(screen.icon, contentDescription = "")
                    },
                    label = { Text(text = screen.title) })
            }
        }
    }) { innerPadding ->
        //deep link test:  adb shell am start -d "android-app://compose.learn/notification/adbTest" -a android.intent.action.VIEW
        NavHost(navController = navController, startDestination = Screen.Home.route, modifier = Modifier.padding(innerPadding)) {
            composable(Screen.Home.route) { navBackStackEntry ->
                //viewModel()以composable为ViewModelStore,作用域只在当前的composable中
                val exampleViewModel: ExampleViewModel = viewModel()
                //多个destination之间共享viewmodel可以使用以下方法传入一个公共的ViewModelStoreOwner,这里是navBackStackEntry, 在多层路由下可以这样使用
                val exampleViewModel2 = hiltViewModel<ExampleViewModel2>(navBackStackEntry)
                Home()
            }
            composable(Screen.Favorite.route) { Favorite() }
            composable(
                Screen.Notification.route,
                deepLinks = listOf(navDeepLink { uriPattern = "$uri/notification/{from}" })
            ) { backStackEntry ->
                Notification(backStackEntry.arguments?.getString("from"))
            }
            composable(Screen.Cart.route) { Cart() }
        }
    }
}

@Composable
fun Home() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Home")
    }
}

@Composable
fun Favorite() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Favorite")
    }
}

@Composable
fun Notification(from: String?) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Notification from $from")
    }
}

@Composable
fun Cart() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Cart")
    }
}

class ExampleViewModel : ViewModel() {

}

@HiltViewModel
class ExampleViewModel2 @Inject constructor() : ViewModel() {

}
