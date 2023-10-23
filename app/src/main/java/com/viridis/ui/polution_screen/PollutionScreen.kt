package com.viridis.ui.polution_screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.viridis.ui.utils.PollutionLevels
import java.lang.NumberFormatException

@Composable
fun PollutionScreen(
    pollutionData: AirQualityData,
    viewModel: PollutionViewModel,
    fetchCountryPollutionData: (country: CountryKeywordEnum) -> Unit
) {
    Column {
        val selectedItem = viewModel.selectedFlag.collectAsState()
        val availableCountries =
            CountryKeywordEnum.values()
        Text(
            modifier = Modifier
                .padding(10.dp),
            text = "Wählen Sie ein Land aus",
            fontStyle = FontStyle.Italic,
            color = Color.LightGray
        )
        LazyRow(modifier = Modifier.padding(0.dp)) {
            items(availableCountries.size) {
                Image(
                    modifier = Modifier
                        .size(
                            if (selectedItem.value == it) {
                                100.dp
                            } else {
                                60.dp
                            }
                        )
                        .padding(10.dp)
                        .clickable {
                            fetchCountryPollutionData(availableCountries[it])
                            viewModel.selectFlag(it)
                        },
                    painter = painterResource(id = availableCountries[it].image),
                    contentDescription = null
                )
            }
        }
        Text(
            modifier = Modifier
                .padding(10.dp),
            text = "Aktuelle Anzeige der Luftqualität in: ${availableCountries[selectedItem.value].germanValue}",
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Bold,
            color = Color.LightGray
        )
        LazyColumn {
            items(pollutionData.data.size) {
                val currentPollutionItem = pollutionData.data[it]
                PollutionItem(currentPollutionItem.station.name, currentPollutionItem.aqi)
            }
        }
    }
}

@Composable
fun PollutionItem(cityName: String, AQI: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        var airQualityInt = -1

        try {
            airQualityInt = AQI.toInt()
        } catch (_: NumberFormatException) {

        }

        val pollutionColor = PollutionLevels.getColorByAQI(airQualityInt).color

        Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            modifier = Modifier
                .background(Color.Transparent)
                .fillMaxWidth(),
            border = BorderStroke(2.dp, Color(pollutionColor))
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.padding(10.dp)
            ) {
                Text(
                    fontSize = 14.sp,
                    text = cityName,
                    modifier = Modifier.padding(bottom = 10.dp),
                    color = Color.DarkGray
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .border(
                                width = 2.dp,
                                color = Color(pollutionColor),
                                shape = CircleShape
                            )
                            .size(36.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            textAlign = TextAlign.Center,
                            fontSize = 18.sp,
                            text = AQI,
                            color = Color.Black
                        )
                    }
                    Text(
                        modifier = Modifier.padding(start = 10.dp),
                        fontSize = 22.sp,
                        text = PollutionLevels.getColorByAQI(airQualityInt).level,
                        color = Color.Black
                    )
                }
            }
        }

    }
}