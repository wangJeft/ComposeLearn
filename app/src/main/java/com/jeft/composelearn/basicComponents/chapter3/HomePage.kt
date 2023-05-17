package com.jeft.composelearn.basicComponents.chapter3

import android.widget.Space
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxColors
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jeft.composelearn.R
import com.jeft.composelearn.ui.theme.body1
import com.jeft.composelearn.ui.theme.caption
import com.jeft.composelearn.ui.theme.gray
import com.jeft.composelearn.ui.theme.h1
import com.jeft.composelearn.ui.theme.h2
import com.jeft.composelearn.ui.theme.pink100
import com.jeft.composelearn.ui.theme.white

data class ImageItem(val name: String, val resId: Int)

val bloomBannerList = listOf(
    ImageItem("Desert chic", R.drawable.desert_chic),
    ImageItem("Tiny terrariums", R.drawable.tiny_terrariums),
    ImageItem("Jungle Vibes", R.drawable.jungle_vibes)
)

val bloomInfoList = listOf(
    ImageItem("Monstera", R.drawable.monstera),
    ImageItem("Aglaonema", R.drawable.aglaonema),
    ImageItem("Peace lily", R.drawable.peace_lily),
    ImageItem("Fiddle leaf tree", R.drawable.fiddle_leaf),
    ImageItem("Desert chic", R.drawable.desert_chic),
    ImageItem("Tiny terrariums", R.drawable.tiny_terrariums),
    ImageItem("Jungle Vibes", R.drawable.jungle_vibes)
)

val navList = listOf(
    ImageItem("Home", R.drawable.ic_home),
    ImageItem("Favorites", R.drawable.ic_favorite_border),
    ImageItem("Profile", R.drawable.ic_account_circle),
    ImageItem("Cart", R.drawable.ic_shopping_cart)
)

@Preview
@Composable
fun HomePage() {
    Scaffold(bottomBar = { BottomBar() }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(white)
                .padding(
                    bottom = it.calculateBottomPadding(),
                    top = it.calculateTopPadding(),
                    start = 16.dp,
                    end = 16.dp
                )
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            SearchBar()
            BloomRowBanner()
            BloomInfoList()
        }
    }
}

@Composable
fun BottomBar() {
    BottomNavigation(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(pink100)
    ) {
        navList.forEach {
            BottomNavigationItem(selected = "Home" == it.name, onClick = { }, icon = {
                Icon(
                    painter = painterResource(id = it.resId),
                    contentDescription = "",
                    modifier = Modifier.size(24.dp)
                )
            }, label = { Text(text = it.name, style = caption, color = gray) })
        }
    }
}

@Composable
fun SearchBar() {
    Box {
        TextField(
            value = "",
            onValueChange = { },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(RoundedCornerShape(4.dp))
                .border(
                    BorderStroke(1.dp, Color.Black)
                ),
            leadingIcon = {
                Icon(
                    painter = rememberVectorPainter(
                        image = ImageVector.vectorResource(
                            id = R.drawable.ic_search
                        )
                    ), contentDescription = "", modifier = Modifier.size(18.dp)
                )
            },
            placeholder = {
                Text(text = "Search", style = body1, color = gray)
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = white,
                unfocusedBorderColor = white,
                focusedBorderColor = white
            )
        )
    }
}

@Composable
fun BloomRowBanner() {
    Column {
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Browse themes",
                style = h1,
                color = gray,
                modifier = Modifier
                    .fillMaxWidth()
                    .paddingFromBaseline(top = 32.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow {
            items(bloomBannerList.size) {
                if (it != 0) {
                    Spacer(modifier = Modifier.width(8.dp))
                }
                PlantCard(plant = bloomBannerList[it])
            }
        }
    }
}

@Composable
fun BloomInfoList() {
    Column {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = "Design your home garden",
                style = h1,
                color = gray,
                modifier = Modifier.paddingFromBaseline(top = 40.dp)
            )

            Icon(
                painter = painterResource(id = R.drawable.ic_filter_list),
                contentDescription = "",
                modifier = Modifier
                    .padding(top = 24.dp)
                    .size(24.dp)
            )

        }


        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(bottom = 56.dp)
        ) {
            items(bloomInfoList.size) {
                if (it != 0) {
                    Spacer(modifier = Modifier.height(8.dp))
                }
                DesignCard(plant = bloomInfoList[it])
            }
        }
    }

}

@Composable
fun DesignCard(plant: ImageItem) {
    Row(Modifier.fillMaxWidth()) {
        Image(
            painter = painterResource(id = plant.resId), contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(4.dp))
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = plant.name,
                        style = h2,
                        color = gray,
                        modifier = Modifier.paddingFromBaseline(top = 24.dp)
                    )
                    Text(
                        text = "This is a description",
                        style = body1,
                        color = gray,
                    )

                }

                Checkbox(
                    checked = false,
                    onCheckedChange = {},
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .size(24.dp),
                    colors = CheckboxDefaults.colors(checkmarkColor = white)
                )
            }
            Divider(color = gray, modifier = Modifier.padding(top = 16.dp), thickness = 0.5.dp)
        }
    }
}

@Composable
fun PlantCard(plant: ImageItem) {
    Card(
        modifier = Modifier
            .size(136.dp)
            .clip(RoundedCornerShape(4.dp))
    ) {
        Column {
            Image(
                painter = painterResource(id = plant.resId),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(96.dp)
            )
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = plant.name,
                    style = h2,
                    color = gray,
                    modifier = Modifier
                        .fillMaxWidth()
                        .paddingFromBaseline(top = 24.dp, bottom = 16.dp)
                )
            }
        }

    }
}