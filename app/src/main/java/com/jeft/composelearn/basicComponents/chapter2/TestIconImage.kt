package com.jeft.composelearn.basicComponents.chapter2

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import com.jeft.composelearn.R

@Composable
fun TestImage() {
    Column {

        //Material Icon默认的tint颜色会是黑色, 所以彩色的icon,也会变成黑色
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.collect),//xml svg矢量图
            contentDescription = "矢量图资源"
        )
        //将tint设置为Color.Unspecified, 即可显示出多色图标
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.collect),//xml svg矢量图
            contentDescription = "矢量图资源", tint = Color.Unspecified
        )

        Icon(
            bitmap = ImageBitmap.imageResource(id = R.drawable.gallery), contentDescription = "图片资源"//加载jpg或png
        )

        Icon(painter = painterResource(id = R.drawable.visibility), contentDescription = "任意类型的资源")//任意类型的资源文件

        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.collect),//xml svg矢量图
            contentDescription = "矢量图资源"
        )

        Image(
            bitmap = ImageBitmap.imageResource(id = R.drawable.gallery), contentDescription = "图片资源"//加载jpg或png
        )

        Image(painter = painterResource(id = R.drawable.visibility), contentDescription = "任意类型的资源")//任意类型的资源文件

    }
}