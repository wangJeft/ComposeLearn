package com.jeft.chatty.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun WidthSpacer(value: Dp) = Spacer(modifier = Modifier.padding(horizontal = value))