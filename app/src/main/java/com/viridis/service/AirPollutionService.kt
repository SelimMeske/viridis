package com.viridis.service

import com.viridis.data.models.AirQualityModel
import retrofit2.http.GET
import retrofit2.http.Query

interface AirPollutionService {

    @GET("https://api.waqi.info/search/")
    suspend fun getAirPollutionData(
        @Query("token")
        token: String = "0e5f1c24acf0fae8b264f8e8a00e9a1df06efe82",
        @Query("keyword")
        keyword: String
    ): AirQualityModel
}