package com.viridis.ui.auth

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.viridis.R
import com.viridis.data.models.SignInStateModel

@Composable
fun SignInScreen(
    state: SignInStateModel,
    isLoadingState: Boolean,
    onSignInClick: () -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = state.signInError) {
        state.signInError?.let { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
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
        if (isLoadingState) {
            Spacer(modifier = Modifier.height(10.dp))
            CircularProgressIndicator(
                modifier = Modifier.width(64.dp),
                color = MaterialTheme.colorScheme.surfaceVariant,
                trackColor = MaterialTheme.colorScheme.secondary,
            )
        }
        Spacer(modifier = Modifier.height(200.dp))
        OutlinedButton(
            onClick = onSignInClick,
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
                text = stringResource(R.string.sign_in),
                fontSize = 22.sp
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row {
            val localUriHandle = LocalUriHandler.current
            val googleSignUpUri = "https://accounts.google.com/v3/signin/identifier?flowName=GlifWebSignIn&flowEntry=ServiceLogin&dsh=S-1156598317%3A1716551773901175&ddm=0"
            Text(
                color = Color.LightGray,
                modifier = Modifier
                    .padding(end = 5.dp),
                text = stringResource(R.string.no_account),
                fontSize = 12.sp
            )
            Text(
                modifier = Modifier.clickable {
                    localUriHandle.openUri(googleSignUpUri)
                },
                color = Color.White,
                text = stringResource(R.string.register_now),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }

    }
}