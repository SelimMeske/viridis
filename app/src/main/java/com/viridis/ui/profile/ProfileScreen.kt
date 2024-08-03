package com.viridis.ui.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.viridis.R
import com.viridis.data.models.UserModel
import com.viridis.ui.shared_components.ColorScheme
import com.viridis.ui.shared_components.CoreButton
import com.viridis.ui.shared_components.ViridisDialog

@Composable
fun ProfileScreen(
    userModel: UserModel,
    onSignOutClick: () -> Unit
) {
    val showPopup = remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    val emailLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { activityResult ->
            if (activityResult.resultCode == Activity.RESULT_OK) {
                Toast.makeText(context, "Vielen Dank für Ihr Feedback :)", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(
                    context,
                    "Ihr Feedback wurde nicht erfolgreich gesendet. Bitte versuchen Sie es erneut.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .padding(bottom = 40.dp)
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.profile_background),
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

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = userModel.profilePictureUrl, contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .border(2.dp, color = Color.White, shape = CircleShape)
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = userModel.username ?: "",
                        fontSize = 20.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Row(
                modifier = Modifier.clickable {
                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse("mailto:")
                        putExtra(Intent.EXTRA_EMAIL, arrayOf("viridis.feedback@gmail.com"))
                        putExtra(Intent.EXTRA_SUBJECT, "Viridis App Feedback")
                    }
                    emailLauncher.launch(intent)
                },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    modifier = Modifier.width(40.dp),
                    painter = painterResource(id = R.drawable.feedback),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(20.dp))
                Text(text = "Senden Sie uns Ihr Feedback", fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(60.dp))

            CoreButton(text = stringResource(R.string.sign_out), colorScheme = ColorScheme.GREEN) {
                onSignOutClick()
            }

            ViridisDialog(showDialog = showPopup.value, onConfirm = { value ->

                val currentMember = FirebaseAuth.getInstance().currentUser
                val email = currentMember?.email ?: return@ViridisDialog

                val credentials = EmailAuthProvider.getCredential(email, value)
                showPopup.value = false
            }, onDismiss = {
                showPopup.value = false
            })
        }
    }
}