package com.viridis.ui.shared_components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CoreButton(
    modifier: Modifier = Modifier,
    text: String,
    colorScheme: ColorScheme = ColorScheme.WHITE,
    onClick: () -> Unit
) {
    val color = if (colorScheme == ColorScheme.WHITE) Color.White else Color(0xFF68B984)

    OutlinedButton(
        onClick = { onClick() },
        colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent),
        border = BorderStroke(2.dp, color), modifier = modifier.padding(top = 20.dp)
    ) {
        Text(fontSize = 18.sp, text = text, color = color)
    }
}