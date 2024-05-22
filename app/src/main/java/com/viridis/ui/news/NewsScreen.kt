package com.viridis.ui.news

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.viridis.R
import com.viridis.data.models.NewsModel
import com.viridis.ui.shared_components.CoreButton

@Composable
fun NewsScreen(
    news: List<NewsModel>,
    navController: NavHostController
) {
    Column {
        LazyColumn {
            items(news.size) {
                if (it == 0) LatestNews(news[it]) { navController.navigate("newsDetails/0") } else NewsItem(news[it]) { navController.navigate("newsDetails/${it}") }
            }
        }
    }
}

@Composable
fun NewsInfo(title: String, _author: String) {
    val author = stringResource(
        R.string.written_by, _author
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 10.dp)
        ) {
            Text(fontSize = 12.sp, fontStyle = FontStyle.Italic, text = author)
            Text(fontSize = 16.sp, text = createTitle(title))
        }
        Text(
            modifier = Modifier
                .padding(12.dp)
                .align(Alignment.BottomEnd),
            fontSize = 8.sp, text = "12 Jan 2022"
        )
    }
}

@Composable
fun LatestNews(news: NewsModel, onClick: () -> Unit) {
    val author = stringResource(
        R.string.written_by, news.author
    )
    Box(
        modifier = Modifier
            .background(Color.White)
            .height(220.dp)
            .fillMaxWidth()
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth(),
            contentScale = ContentScale.FillWidth,
            model = news.image,
            contentDescription = null
        )
        Box(
            modifier = Modifier
                .background(Color.Black.copy(alpha = 0.4f))
                .fillMaxSize()
        )
        Column(
            modifier = Modifier
                .align(Alignment.Center)
        ) {
            Text(color = Color.White, text = stringResource(R.string.hot_news_for_you))
            Text(
                text = createTitle(news.title),
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 20.sp

            )
            Text(color = Color.White, text = author)
            CoreButton(text = stringResource(R.string.read_more)) {
                onClick()
            }
        }

    }
}

private fun createTitle(title: String): String {
    val maxCharactersAllowed = 32
    return if (title.length > maxCharactersAllowed) {
        "${title.slice(0..maxCharactersAllowed)}..."
    } else {
        title
    }
}