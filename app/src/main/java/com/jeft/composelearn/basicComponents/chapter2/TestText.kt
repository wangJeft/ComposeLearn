package com.jeft.composelearn.basicComponents.chapter2

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.jeft.composelearn.R


@Composable
fun TestText() {
    Column(modifier = Modifier.verticalScroll(state = rememberScrollState())) {
        Text(text = "hello world")
        Text(text = stringResource(id = R.string.hello_world))
        Text(
            text = stringResource(id = R.string.hello_world),
            style = TextStyle(
                fontSize = 25.sp,//字体大小
                fontWeight = FontWeight.Bold,//字体粗细
                fontStyle = FontStyle.Italic,//斜体,
                background = Color.Cyan,
                lineHeight = 35.sp,//行高
            ),
        )
        Text(
            text = stringResource(id = R.string.hello_world),
            style = TextStyle(color = Color.Gray, letterSpacing = 4.sp)
        )
        Text(
            text = stringResource(id = R.string.hello_world),
            style = TextStyle(textDecoration = TextDecoration.LineThrough)
        )

        Text(
            text = stringResource(id = R.string.hello_world),
            style = MaterialTheme.typography.h6.copy(fontStyle = FontStyle.Italic)
        )

        Text(
            text = "Hello Compose, Compose是下一代Android UI工具包, 开发起来和以往XML写布局有着非常大的不同",
            style = MaterialTheme.typography.body1
        )
        Text(
            text = "Hello Compose, Compose是下一代Android UI工具包, 开发起来和以往XML写布局有着非常大的不同",
            style = MaterialTheme.typography.body1,
            maxLines = 1
        )
        Text(
            text = "Hello Compose, Compose是下一代Android UI工具包, 开发起来和以往XML写布局有着非常大的不同",
            style = MaterialTheme.typography.body1,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )

        Text(text = stringResource(id = R.string.hello_world), fontFamily = FontFamily.Cursive)
        Text(text = stringResource(id = R.string.hello_world), fontFamily = FontFamily.Monospace)
        Text(
            text = "你好,我的世界", fontFamily = FontFamily(Font(R.font.test_font))
        )

        Text(text = buildAnnotatedString {
            appendLine("-------------------------------")
            withStyle(style = SpanStyle(fontSize = 24.sp)) {
                append("你现在学的章节是")
            }
            withStyle(style = SpanStyle(fontWeight = FontWeight.W900, fontSize = 24.sp)) {
                append("Text")
            }
            appendLine()
            withStyle(style = ParagraphStyle(lineHeight = 25.sp)) {
                append("在刚刚讲过的内容中, 我们学会了如何应用文字样式, 以及如何限制文本的行数和处理溢出的视觉效果")
            }
            appendLine()
            append("现在,我们正在学习")
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.W900,
                    textDecoration = TextDecoration.Underline,
                    color = Color(0xFF59A869)
                )
            ) {
                append("AnnotatedString")
            }

        })
        val annotatedString = buildAnnotatedString {
            withStyle(style = ParagraphStyle()) {
                append("点击下面的链接查看更多")
                pushStringAnnotation(
                    tag = "URL", annotation = "https://jetpackcompose.cn/docs/elements/text"
                )
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.W900,
                        textDecoration = TextDecoration.Underline,
                        color = Color(0xFF59A869)
                    )
                ) {
                    append("参考链接")
                }
                pop()//结束之前添加的样式
                appendLine("-------------------------------")
            }
        }

        val currentContext = LocalContext.current
        ClickableText(text = annotatedString, onClick = { offset ->
            annotatedString.getStringAnnotations(tag = "URL", start = offset, end = offset)
                .firstOrNull()?.let { annotation ->
                    val uri: Uri = Uri.parse(annotation.item)
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    startActivity(currentContext, intent, null)
                }
        })

        SelectionContainer {
            Text(text = "可以被选中复制的文字")
        }
        TestTextField()
    }
}

@Composable
fun TestTextField() {
    var textFields by remember { mutableStateOf("") }
    TextField(value = textFields,
        onValueChange = { textFields = it },
        label = { Text(text = "user name") })

    var userName by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    TextField(value = userName,
        onValueChange = { userName = it },
        label = { Text(text = "用户名") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.AccountBox, contentDescription = "username"
            )
        })

    TextField(value = password,
        onValueChange = { password = it },
        label = { Text(text = "密码") },
        trailingIcon = {
            IconButton(onClick = {}) {
                Icon(
                    painter = painterResource(id = R.drawable.visibility),
                    contentDescription = "password"
                )
            }
        })
    var outlinedTextFields by remember { mutableStateOf("Hello World") }
    OutlinedTextField(value = outlinedTextFields, onValueChange = { outlinedTextFields = it })

    //不是basicTextField,属性被material theme所限制,例如修改高度,输入区域会被截断,影响显示效果
    TextField(value = userName,
        onValueChange = { userName = it },
        label = { Text(text = "用户名") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.AccountCircle, contentDescription = null
            )
        },
        modifier = Modifier.height(30.dp)
    )


    var basicTextFields by remember { mutableStateOf("") }

    BasicTextField(
        value = basicTextFields,
        onValueChange = { basicTextFields = it },
        decorationBox = { innerTextField ->
            Column {
                innerTextField.invoke()//innerTextField代表输入框开始输入的位置,需要在合适的地方调用
                Divider(
                    thickness = 1.dp, modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Black)
                )
            }
        },
        modifier = Modifier
            .height(40.dp)
            .background(Color.Cyan),
    )
    Spacer(modifier = Modifier.height(15.dp))
    SearchBar()
    Spacer(modifier = Modifier.height(15.dp))

}

@Composable
fun SearchBar() {
    var searchText by remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Color(0xFFD3D3D3)),
        contentAlignment = Alignment.Center
    ) {
        BasicTextField(
            value = searchText,
            onValueChange = { searchText = it },
            decorationBox = { innerTextField ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 2.dp, horizontal = 8.dp)
                ) {
                    Icon(imageVector = Icons.Filled.Search, contentDescription = "")
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .weight(1f),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (searchText.isEmpty()) {
                            Text(text = "搜索", style = TextStyle(color = Color(0, 0, 0, 128)))
                        }
                        innerTextField()
                    }
                    if (searchText.isNotEmpty()) {
                        IconButton(
                            onClick = { searchText = "" }, modifier = Modifier.size(16.dp)
                        ) {
                            Icon(imageVector = Icons.Filled.Clear, contentDescription = "")
                        }
                    }
                }
            },
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .height(30.dp)
                .fillMaxWidth()
                .background(Color.White, CircleShape),
        )
    }
}