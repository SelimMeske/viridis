package com.viridis.data.models

data class StationModel(
    val uid: Int,
    val aqi: String,
    val time: TimeModel,
    val station: StationInfoModel
)