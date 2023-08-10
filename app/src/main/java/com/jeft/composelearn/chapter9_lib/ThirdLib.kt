package com.jeft.composelearn.chapter9_lib

import android.widget.Space
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import coil.size.Dimension
import coil.size.Size
import com.google.accompanist.insets.ui.TopAppBar
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jeft.composelearn.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@Composable
fun SystemUiTest() {
    Box(modifier = Modifier.fillMaxSize()) {

        val systemUiController = rememberSystemUiController()
        val useDarkIcons = MaterialTheme.colors.isLight
        SideEffect {
            systemUiController.setSystemBarsColor(color = Color.Transparent, darkIcons = useDarkIcons)
        }
//        TopAppBar(title = { Text(text = "TopAppBar") }, modifier = Modifier.statusBarsPadding(), backgroundColor = Color.Gray)
        //使用com.google.accompanist:accompanist-insets-ui:0.30.1 实现沉浸式状态栏的效果
        TopAppBar(title = { Text(text = "TopAppBar") }, backgroundColor = Color.Gray, contentPadding = WindowInsets.statusBars.asPaddingValues())

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerTest() {
    Box(modifier = Modifier.fillMaxSize()) {
        val pagerState = rememberPagerState()
        val scope = rememberCoroutineScope()
        HorizontalPager(pageCount = 3, modifier = Modifier.fillMaxSize(), state = pagerState) { page ->
            when (page) {
                0 -> ColorBox(color = Color.Blue, pageIndex = page)
                1 -> ColorBox(color = Color.Cyan, pageIndex = page)
                2 -> ColorBox(color = Color.Magenta, pageIndex = page)
            }
        }
        SideEffect {
            scope.launch {
                delay(3000)
                pagerState.scrollToPage(2)
            }
        }
    }
}

@Composable
fun ColorBox(color: Color, pageIndex: Int) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = color), contentAlignment = Alignment.Center
    ) {
        Text(text = "page $pageIndex")
    }
}

@Preview
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeRefreshTest() {
    Box(modifier = Modifier.fillMaxSize()) {
        val viewModel: MyViewModel = viewModel()
        val isRefreshing by viewModel.isRefreshing.collectAsState()
        val background by animateColorAsState(targetValue = viewModel.background, animationSpec = tween(1000), label = "backgroundAnim")
        /* SwipeRefresh(state = rememberSwipeRefreshState(isRefreshing), onRefresh = { viewModel.refresh() }) {
             Box(
                 modifier = Modifier
                     .fillMaxSize()
                     .verticalScroll(rememberScrollState())
                     .background(background)
             )
         }*/
        val pullRefreshState = rememberPullRefreshState(refreshing = isRefreshing, onRefresh = { viewModel.refresh() })
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pullRefresh(pullRefreshState)
                .verticalScroll(rememberScrollState())
                .background(background),
        ) {
            PullRefreshIndicator(isRefreshing, pullRefreshState, modifier = Modifier.align(Alignment.TopCenter))
        }
    }
}

class MyViewModel : ViewModel() {
    private val _isRefreshing = MutableStateFlow(false)
    private val colorPanel = listOf(Color.Gray, Color.Red, Color.Black, Color.Cyan, Color.DarkGray, Color.LightGray, Color.Yellow)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing

    var background by mutableStateOf(Color.Gray)

    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.emit(true)
            delay(1000)
            background = colorPanel.random()
            _isRefreshing.emit(false)
        }
    }

}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FlowLayoutTest() {
    Column(modifier = Modifier.fillMaxSize()) {
        FlowRow(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically, maxItemsInEachRow = 3) {
            Text(text = "text1")
            Text(text = "text2")
            Text(text = "text3")
            Text(text = "text4")
            Text(text = "text5")
            Text(text = "text6")
        }
        Divider(modifier = Modifier.fillMaxWidth())
        FlowColumn(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.SpaceEvenly, horizontalAlignment = Alignment.CenterHorizontally, maxItemsInEachColumn = 3) {
            Text(text = "text1")
            Text(text = "text2")
            Text(text = "text3")
            Text(text = "text4")
            Text(text = "text5")
            Text(text = "text6")
        }
    }
}

@Composable
fun CoilTest() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        val imageUrl = "https://t7.baidu.com/it/u=3478850435,1363883222&fm=193&f=GIF"
        AsyncImage(model = imageUrl, contentDescription = "")
        Divider(
            modifier = Modifier
                .height(2.dp)
                .fillMaxWidth()
        )

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
//                .size(120, 84)
//                .size(Size(Dimension(120), Dimension(84)))
                .crossfade(true)
                .build(),
            placeholder = painterResource(id = R.drawable.img),
            error = painterResource(id = R.drawable.aglaonema),
            onSuccess = {},
            contentDescription = ""
        )
        Divider(
            modifier = Modifier
                .height(2.dp)
                .fillMaxWidth()
        )
        SubcomposeAsyncImage(model = imageUrl, loading = { CircularProgressIndicator() }, contentDescription = "")
        Divider(
            modifier = Modifier
                .height(2.dp)
                .fillMaxWidth()
        )
        SubcomposeAsyncImage(model = imageUrl, contentDescription = "") {
            if (painter.state is AsyncImagePainter.State.Loading || painter.state is AsyncImagePainter.State.Error) {
                CircularProgressIndicator()
            } else {
             SubcomposeAsyncImageContent()
            }
        }
    }
}