package com.jeft.composelearn.basicComponents.chapter3

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jeft.composelearn.R
import com.jeft.composelearn.ui.theme.Shapes
import com.jeft.composelearn.ui.theme.button
import com.jeft.composelearn.ui.theme.gray
import com.jeft.composelearn.ui.theme.pink100
import com.jeft.composelearn.ui.theme.pink900
import com.jeft.composelearn.ui.theme.subtitle1
import com.jeft.composelearn.ui.theme.white

@Preview
@Composable
fun WelcomePage() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(pink100)
    ) {
        Image(
            painter = rememberVectorPainter(image = ImageVector.vectorResource(id = R.drawable.ic_light_welcome_bg)),
            contentDescription = "",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        WelcomeContent()
    }
}

@Composable
fun WelcomeContent() {
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(72.dp))
        LeafImage()
        Spacer(modifier = Modifier.height(48.dp))
        WelcomeTitle()
        Spacer(modifier = Modifier.height(40.dp))
        WelcomeButtons()
    }
}

@Composable
fun LeafImage() {
    Image(
        painter = rememberVectorPainter(image = ImageVector.vectorResource(id = R.drawable.ic_light_welcome_illos)),
        contentDescription = "",
        modifier = Modifier
            .wrapContentSize()
            .padding(start = 88.dp)
    )
}

@Composable
fun WelcomeTitle() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = rememberVectorPainter(image = ImageVector.vectorResource(id = R.drawable.ic_light_logo)),
            contentDescription = "",
            modifier = Modifier
                .wrapContentWidth()
                .height(23.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Text(
                text = "Beautiful home garden solutions",
                textAlign = TextAlign.Center,
                style = subtitle1,
                color = gray
            )
        }

    }
}

@Composable
fun WelcomeButtons() {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Button(
            onClick = { },
            modifier = Modifier
                .height(48.dp)
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .clip(Shapes.medium),
            colors = ButtonDefaults.buttonColors(backgroundColor = pink900)
        ) {
            Text(text = "Create account", style = button, color = white)

        }
        Spacer(modifier = Modifier.height(24.dp))
        TextButton(onClick = { }) {
            Text(text = "Log in", style = button, color = pink900)
        }
    }
}