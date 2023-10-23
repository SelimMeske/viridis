package com.viridis.ui.signIn

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.viridis.R

@Composable
fun SignInScreen(
    state: SignInState,
    onSignInClick: () -> Unit
) {
    LaunchedEffect(key1 = state.signInError) {
        state.signInError?.let {
            
        }
    }
    
    Column(
        modifier = Modifier
            .paint(
                painterResource(id = R.drawable.bcg2),
                contentScale = ContentScale.FillBounds
            )
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painterResource(id = R.drawable.vv22_white),
            null,
            modifier = Modifier
                .size(180.dp)
        )
        Spacer(modifier = Modifier.height(200.dp))
        OutlinedButton(
            onClick = onSignInClick,
            //colors = ButtonDefaults.outlinedButtonColors(containerColor = Color(0xFF68B984)),
            border = BorderStroke(2.dp, Color.White)
        ) {
            Image(
                painterResource(id = R.drawable.google_icon),
                contentDescription = null,
                modifier = Modifier
                    .size(32.dp)
                    .padding(end = 10.dp)
            )
            Text(
                color = Color.White,
                text = "Anmelden",
                fontSize = 22.sp
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row {
            Text(
                color = Color.LightGray,
                modifier = Modifier
                    .padding(end = 5.dp),
                text = "Sie haben kein Konto?",
                fontSize = 12.sp
            )
            Text(
                color = Color.White,
                text = "Registrieren Sie sich jetzt!",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }

    }
}