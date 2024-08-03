package com.viridis.ui.intro

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.viridis.R

@Composable
fun IntroScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            Image(
                painter = painterResource(id = R.drawable.sea),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
        }

        item {
            Text(
                text = stringResource(id = R.string.sustainability_title),
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 24.sp,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(16.dp)
            )
        }

        item {
            Text(
                text = stringResource(id = R.string.sustainability_content),
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 16.sp,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
            )
        }

        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.sea_2),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.5f))
                )

                Text(
                    text = stringResource(id = R.string.earth_loves_you),
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp)
                )
            }
        }
    }
}