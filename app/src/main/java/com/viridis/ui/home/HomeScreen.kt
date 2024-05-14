package com.viridis.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.viridis.R
import com.viridis.ui.news.NewsItem
import com.viridis.data.models.NewsModel

@Composable
fun HomeScreen(
    userName: String,
    profileImage: String,
    latestNews: List<NewsModel>,
    navController: NavHostController
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        HomeTopContainer(userName, profileImage)
        HomeWelcomeCard(R.drawable.home_card_image, "Willkommen","Was ist Nachhaltigkeit und wo soll ich anfangen?") {

        }
        Text(
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 16.dp),
            fontSize = 20.sp,
            text = "Aktuelle Nachrichten"
        )
        Column {
            if (latestNews.isNotEmpty()) {
                for (i in 0..3) {
                    NewsItem(latestNews[i]) { navController.navigate("newsDetails/${i}") }
                }
            }
        }
        HomeWelcomeCard(R.drawable.pollution, "Luftqualität!","Wie gut ist die Luft? Das zeigt der Luftqualitätsindex") {
            navController.navigate("pollutionScreen")

        }
    }
}