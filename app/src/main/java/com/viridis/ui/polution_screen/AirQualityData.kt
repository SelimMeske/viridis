package com.viridis.ui.polution_screen

data class AirQualityData(
    val status: String = "",
    val data: List<Station> = emptyList()
)

data class Station(
    val uid: Int,
    val aqi: String,
    val time: Time,
    val station: StationInfo
)

data class Time(
    val tz: String,
    val stime: String,
    val vtime: Long
)

data class StationInfo(
    val name: String,
    val geo: List<Double>,
    val url: String
)
