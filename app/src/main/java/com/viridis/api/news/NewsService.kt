package com.viridis.api.news

import com.viridis.ui.news.model.NewsModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface NewsService {
    @GET("news.json")
    suspend fun getNews(): Response<List<NewsModel>>

    @POST("tracker/{user_id}.json")
    suspend fun postTrackerDate(
        @Path(value = "user_id", encoded = true) id: String,
        @Body payload: String
    ): Response<Any>

    @GET("tracker/{user_id}.json")
    suspend fun getTrackerDate(
        @Path(
            value = "user_id",
            encoded = true
        ) id: String
    ): Response<Map<String, String>>
}