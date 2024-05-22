package com.viridis.ui.eco_tracker

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.viridis.R
import com.viridis.ui.shared_components.ColorScheme
import com.viridis.ui.shared_components.CoreButton

@Composable
fun EcoTrackerScreen(
    viewModel: EcoTrackerViewModel = hiltViewModel()
) {
    val datesState by viewModel.dateState.collectAsStateWithLifecycle()
    val checkInButtonState by viewModel.checkInButtonState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Card(
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .height(340.dp),
            border = BorderStroke(2.dp, color = if (checkInButtonState) Color(0xFF68B984) else Color.LightGray),
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent
            )
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                val daysOfCurrentWeek = viewModel.getUpcoming7Days()

                Image(
                    painter = painterResource(
                        id =
                        if (checkInButtonState) {
                            R.drawable.saved_planet
                        } else {
                            R.drawable.polluted_planet
                        }
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .size(120.dp)
                        .padding(vertical = 20.dp)
                )

                Text(
                    text = stringResource(R.string.every_day_counts),
                    fontStyle = FontStyle.Italic,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 20.dp)
                )

                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {
                    for (i in daysOfCurrentWeek) {
                        Text(
                            fontWeight = FontWeight.Bold,
                            text = i.dayName.slice(0..2)
                        )
                    }
                }
                Box {
                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    ) {
                        for (i in daysOfCurrentWeek) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(shape = CircleShape)
                                    .background(
                                        color = if (
                                            datesState.contains(i.stringDate)
                                        ) Color(0xFF68B984) else Color.LightGray
                                    )
                            ) {
                                Text(
                                    text = i.day,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
                val context = LocalContext.current

                CoreButton(text = stringResource(R.string.check_in), colorScheme = ColorScheme.GREEN) {
                    if (checkInButtonState) {
                        Toast.makeText(
                            context,
                            R.string.already_checked_in,
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        viewModel.setTrackedDate()
                    }
                }
            }
        }
        Column (
            modifier = Modifier.padding(horizontal = 10.dp)
        ) {
            Text(fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, text = stringResource(R.string.eco_tracker_title), modifier = Modifier.padding(bottom = 20.dp))
            Text(text = context.getString(R.string.what_is_virids_tracker), fontSize = 16.sp, modifier = Modifier.padding(bottom = 20.dp))
        }
    }
}