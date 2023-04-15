package com.jeft.composelearn.basicComponents.chapter2

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun TestList() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray),
        contentPadding = PaddingValues(35.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items((1..50).toList()) { index ->
            ContentCard(index = index)
        }
    }
}

@Composable
fun ContentCard(index: Int) {
    Card(elevation = 8.dp, modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp), contentAlignment = Alignment.Center
        ) {
            Text(text = "第${index}卡片", style = MaterialTheme.typography.h5)
        }
    }
}