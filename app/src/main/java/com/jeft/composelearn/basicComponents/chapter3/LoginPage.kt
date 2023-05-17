package com.jeft.composelearn.basicComponents.chapter3

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jeft.composelearn.ui.theme.Shapes
import com.jeft.composelearn.ui.theme.body1
import com.jeft.composelearn.ui.theme.body2
import com.jeft.composelearn.ui.theme.button
import com.jeft.composelearn.ui.theme.gray
import com.jeft.composelearn.ui.theme.h1
import com.jeft.composelearn.ui.theme.pink100
import com.jeft.composelearn.ui.theme.pink900
import com.jeft.composelearn.ui.theme.white

@Preview
@Composable
fun LoginPage() {
    Column(
        Modifier
            .fillMaxSize()
            .background(pink100)
            .padding(horizontal = 16.dp)
    ) {
        LoginTitle()
        LoginInputBox()
        HintWithUnderline()
        LoginButton()
    }
}

@Composable
fun LoginTitle() {
    Text(
        text = "Log in with email",
        modifier = Modifier
            .fillMaxWidth()
            .paddingFromBaseline(top = 184.dp, bottom = 16.dp),
        style = h1,
        textAlign = TextAlign.Center
    )
}

@Composable
fun LoginInputBox() {
    Column {
        LoginTextField(placeHolder = "Email address")
        Spacer(modifier = Modifier.height(8.dp))
        LoginTextField(placeHolder = "Password(8 + Characters)")

    }
}
@Composable
fun LoginTextField(placeHolder: String) {
    OutlinedTextField(value = "",
        onValueChange = {},
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(Shapes.small),
        placeholder = { Text(text = placeHolder, style = body1, color = gray) })
}

@Composable
fun HintWithUnderline() {
    val annotatedString = buildAnnotatedString {
        withStyle(style = body2.toSpanStyle()) {
            append("By Clicking below you agree to our ")
            pushStringAnnotation("URL", "https://www.baidu.com")
            withStyle(
                style = body2.copy(textDecoration = TextDecoration.Underline).toSpanStyle()
            ) {
                append("Terms of Use")
            }
            appendLine(" and consent")
            append("to out ")
            pushStringAnnotation("URL", "https://www.google.com")
            withStyle(
                style = body2.copy(textDecoration = TextDecoration.Underline).toSpanStyle()
            ) {
                append("Privacy Policy")
            }
            append(".")

        }
    }
//        Text(text = annotatedString, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center, )
    ClickableText(
        text = annotatedString,
        onClick = {},
        modifier = Modifier.fillMaxWidth().paddingFromBaseline(top = 24.dp, bottom = 16.dp),
        style = TextStyle.Default.copy(textAlign = TextAlign.Center)
    )
}

@Composable
fun LoginButton() {
    Button(
        onClick = {}, modifier = Modifier
            .height(48.dp)
            .fillMaxWidth()
            .clip(Shapes.medium),
        colors = ButtonDefaults.buttonColors(backgroundColor = pink900)
    ) {
        Text(text = "Log in", style = button, color = white)
    }
}