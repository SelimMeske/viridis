package com.viridis.ui.news

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.viridis.R

@Composable
fun NewsDetailScreen(
    index: Int = 0,
    viewModel: NewsViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.fetchNews()
    }

    val news by viewModel.newsState.collectAsState()
    if (news.isNotEmpty()) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            val currentNews = news[index]
            AsyncImage(
                currentNews.image,
                null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.FillWidth
            )
            Column(modifier = Modifier.padding(start = 20.dp, end = 20.dp)) {
                Text(modifier = Modifier.padding(top = 10.dp), fontSize = 20.sp, fontWeight = FontWeight.Bold, text = currentNews.title)
                Text(modifier = Modifier.padding(top = 10.dp), fontStyle = FontStyle.Italic, text = stringResource(
                    R.string.written_by, currentNews.author
                )
                )
                Text(modifier = Modifier.padding(top = 20.dp), text = currentNews.content)
            }
        }
    }
}