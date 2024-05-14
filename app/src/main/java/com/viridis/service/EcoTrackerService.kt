package com.viridis.service

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EcoTrackerService {

    @POST("tracker/{user_id}.json")
    suspend fun postTrackerDate(
        @Path(value = "user_id", encoded = true) id: String,
        @Body payload: String
    ): Any

    @GET("tracker/{user_id}.json")
    suspend fun getTrackerDate(
        @Path(
            value = "user_id",
            encoded = true
        ) id: String
    ): Map<String, String>
}