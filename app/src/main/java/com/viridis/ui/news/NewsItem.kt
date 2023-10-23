package com.viridis.ui.news

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.viridis.ui.news.model.NewsModel

@Composable
fun NewsItem(news: NewsModel, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier
            .background(Color.White)
            .height(120.dp)
            .padding(10.dp)
            .fillMaxSize()
            .clickable {
                onClick()
            },
        border = BorderStroke(1.dp, Color.LightGray)
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
        ) {
            AsyncImage(
                model = news.image,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight()
            )
            NewsInfo(news.title, news.author)
        }
    }
}