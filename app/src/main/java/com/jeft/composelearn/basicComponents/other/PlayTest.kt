package com.jeft.composelearn.basicComponents.other

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jeft.composelearn.R

@Preview
@Composable fun PlayTest() {
    Button(onClick = { }, contentPadding = PaddingValues(0.dp), colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)) {
        Box {
            Image(painter = painterResource(id = R.drawable.bg_button), contentDescription = "")
            Text(
                text = "button", modifier = Modifier
                    .align(alignment = Alignment.Center)
                    .paddingFromBaseline(bottom = 5.dp)
            )
        }
    }


    IconButton(onClick = { /*TODO*/ }) {

    }
}