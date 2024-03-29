package com.jeft.composelearn.chapter8_nav

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun NavigationTest() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "firstPage") {
        composable("firstPage") {
            FirstPage() {
                navController.navigate("secondPage/firstPage") {
//                    popUpTo("firstPage")
                }
            }
        }

        composable(
            "secondPage/{fromPage}?isFirst={isFirst}",
            arguments = listOf(
                navArgument("fromPage") { type = NavType.StringType },
                navArgument("isFirst") {
                    type = NavType.BoolType
                    defaultValue = true
                    nullable = true
                }
            )
        ) { navBackStackEntry ->
            SecondPage(navBackStackEntry.arguments?.getString("fromPage") ?: "") {
                navController.navigate("thirdPage")
//                navController.popBackStack()
//                navController.popBackStack("firstPage", false)
                /*navController.navigate("firstPage") {
                    popUpTo("firstPage")

                }*/
            }
        }

        composable("thirdPage") {
            ThirdPage() {
                navController.navigate("firstPage") {
                    launchSingleTop = true
                    popUpTo("firstPage")
                }
            }
        }

    }
}

@Preview
@Composable
fun FirstPage(toSecondPage: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Cyan)
    ) {
        Log.d("NavigationTest", "FirstPage: ")
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(text = "first page", modifier = Modifier.align(alignment = Alignment.CenterHorizontally))
        }

        Button(onClick = { toSecondPage() }, modifier = Modifier.align(Alignment.Center)) {
            Text(text = "go to second page")
        }
    }
}

@Preview
@Composable
fun SecondPage(fromPage: String = "", toFirstPage: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Magenta)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = "second page: $fromPage", modifier = Modifier.align(alignment = Alignment.CenterHorizontally))
        }

        Button(onClick = { toFirstPage() }, modifier = Modifier.align(Alignment.Center)) {
            Text(text = "go to third page")
        }
    }
}

@Preview
@Composable
fun ThirdPage(toFirstPage: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = "third page: ", modifier = Modifier.align(alignment = Alignment.CenterHorizontally))
        }

        Button(onClick = { toFirstPage() }, modifier = Modifier.align(Alignment.Center)) {
            Text(text = "go to first page")
        }
    }
}