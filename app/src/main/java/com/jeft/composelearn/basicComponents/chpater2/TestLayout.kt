package com.jeft.composelearn.basicComponents.chpater2

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.jeft.composelearn.R

@Composable
fun TestLayout() {

    Column(modifier = Modifier.border(2.dp, color = MaterialTheme.colors.primary)) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 10.dp)
                .fillMaxWidth(),
            elevation = 10.dp
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(text = "Jetpack Compose是什么", style = MaterialTheme.typography.h6)
                Spacer(modifier = Modifier.padding(vertical = 5.dp))
                Text(
                    text = "Jetpack Compose是用于构建原生Android UI的现代工具包。它采用声明式UI的设计，拥有更简单的自定义和实时的交互预览功能，由Android官方团队全新打造的UI框架。Jetpack Compose可简化并加快Android上的界面开发，使用更少的代码、强大的工具和直观的Kotlin API，快速打造生动而精彩的应用。"
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Filled.Favorite, null)
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Filled.Add, null)
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Filled.Star, null)
                    }
                }

            }
        }

        Spacer(modifier = Modifier.height(40.dp))
        Text(text = "约束布局")
        Spacer(modifier = Modifier.height(40.dp))

        ConstraintLayout(
            modifier = Modifier
                .width(300.dp)
                .height(100.dp)
                .padding(10.dp)
        ) {
            val (portraitImageRef, usernameTextRef, desTextRef) = remember {
                createRefs()//createRefs最多创建16个引用
            }
            Image(painter = painterResource(id = R.drawable.img),
                contentDescription = "",
                modifier = Modifier.constrainAs(portraitImageRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                })
            Text(text = "舞蹈系学姐",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.constrainAs(usernameTextRef) {
                    top.linkTo(portraitImageRef.top)
                    start.linkTo(portraitImageRef.end, 10.dp)
                })
            Text(text = "一本非常好看的漫画",
                style = MaterialTheme.typography.body1,
                modifier = Modifier.constrainAs(desTextRef) {
                    top.linkTo(usernameTextRef.bottom, 10.dp)
                    start.linkTo(usernameTextRef.start)
                })
        }

    }

}