package com.viridis.ui.home

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.viridis.R

@Composable
fun HomeTopContainer(
    userName: String,
    profilePicture: String
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .height(340.dp)
            .fillMaxWidth()
            .paint(
                painterResource(id = R.drawable.home_background),
                contentScale = ContentScale.FillWidth
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween

            ) {
            Column {
                Text(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    text = "Hi $userName",
                    color = Color.White
                )
                Text(
                    fontSize = 12.sp,
                    text = "Wir freuen uns, dass Sie zurück sind!",
                    color = Color.LightGray
                )
            }
            AsyncImage(
                model = profilePicture, contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .border(1.dp, color = Color.White, shape = CircleShape)
            )
        }
    }
}