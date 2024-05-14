package com.viridis.data.repositories

import com.viridis.data.models.AirQualityModel
import kotlinx.coroutines.flow.Flow

interface AirPollutionRepository {

    fun fetchAirPollutionData(keyword: String): Flow<AirQualityModel>
}