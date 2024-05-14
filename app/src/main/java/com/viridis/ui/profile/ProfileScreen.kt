package com.viridis.ui.profile

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.viridis.data.models.UserModel
import com.viridis.ui.shared_components.ColorScheme
import com.viridis.ui.shared_components.CoreButton

@Composable
fun ProfileScreen(
    userModel: UserModel,
    onSignOutClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Column(
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = userModel.profilePictureUrl, contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .border(1.dp, color = Color.White, shape = CircleShape)
            )
            Text(text = userModel.username ?: "")
            CoreButton(text = "Ausloggen", colorScheme = ColorScheme.GREEN) {
                onSignOutClick()
            }
        }
    }
}