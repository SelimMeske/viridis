package com.viridis.data.models

data class AirQualityModel(
    val status: String = "",
    val data: List<StationModel> = emptyList()
)