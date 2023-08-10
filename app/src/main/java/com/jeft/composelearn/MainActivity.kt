package com.jeft.composelearn

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.WindowCompat
import com.jeft.composelearn.chapter7_Gestures.AutoNestedScroll
import com.jeft.composelearn.chapter7_Gestures.ClickGestures
import com.jeft.composelearn.chapter7_Gestures.DragSample
import com.jeft.composelearn.chapter7_Gestures.DragSample2
import com.jeft.composelearn.chapter7_Gestures.Gestures2
import com.jeft.composelearn.chapter7_Gestures.Gestures3
import com.jeft.composelearn.chapter7_Gestures.ScrollableSample
import com.jeft.composelearn.chapter7_Gestures.SwipeableSample
import com.jeft.composelearn.chapter8_nav.Navigation2
import com.jeft.composelearn.chapter8_nav.NavigationTest
import com.jeft.composelearn.chapter9_lib.CoilTest
import com.jeft.composelearn.chapter9_lib.FlowLayoutTest
import com.jeft.composelearn.chapter9_lib.PagerTest
import com.jeft.composelearn.chapter9_lib.SwipeRefreshTest
import com.jeft.composelearn.chapter9_lib.SystemUiTest
import com.jeft.composelearn.ui.theme.ComposeLearnTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            ComposeLearnTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    //chapter2
//                    TestText()
//                    TestImage()
//                    TestButton()
//                    TestSelector()
//                    TestDialog()
//                    TestProgress()
//                    TestLayout()
//                    TestScaffold()
//                    TestList()

                    //chapter3
//                    WelcomePage()
//                    LoginPage()
//                    HomePage()


                    //chapter4
//                    CounterScreen()
//                    CounterScreen2()

                    //chapter5
//                    IntrinsicsMeasure()
//                    TwoText(text1 = "start", text2 = "end")
//                    DrawCache()
//                    WaveLoadingDemo()

                    //chapter6
//                    AnimatedVisibilityTest()
//                    AnimatedContentTest()
//                    CrossfadeTest()
//                    LowLevelAnimateApiTest()
//                    AnimatedShimmerItem()
//                    FavoriteButton()

                    //gestures
//                    ClickGestures()
//                    ScrollableSample()
//                    DragSample()
//                    DragSample2()
//                    SwipeableSample()
//                    Gestures3()

                    //chapter8
//                    NavigationTest()
//                    Navigation2()

                    //chapter9
//                    PagerTest()
//                    SwipeRefreshTest()
//                    FlowLayoutTest()
//                    SystemUiTest()
                    CoilTest()


//                    Greeting("Android")
//                    NativeWebView("https://jetpackcompose.cn")
//                    ModifierTest()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeLearnTheme {
        Greeting("Android")
    }
}

@Composable
fun NativeWebView(url: String) {
    AndroidView(factory = { context ->
        WebView(context).apply {
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()
            loadUrl(url)
        }
    }, modifier = Modifier.fillMaxSize())
}

@Composable
fun ModifierTest() {
    val verticalBrush = Brush.verticalGradient(colors = listOf(Color.Red, Color.Green, Color.Blue))
    Row {
        Box(
            modifier = Modifier
                .size(width = 100.dp, height = 100.dp)
                .offset(x = 100.dp, y = 100.dp)
                .background(brush = verticalBrush)
                .padding(8.dp)
                .border(2.dp, Color.Cyan, shape = RoundedCornerShape(2.dp))
                .padding(8.dp)
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .background(Color.Magenta)
            )
        }
    }
}