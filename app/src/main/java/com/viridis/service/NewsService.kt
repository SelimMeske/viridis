package com.viridis.service

import com.viridis.data.models.NewsModel
import retrofit2.http.GET

interface NewsService {

    @GET("news.json")
    suspend fun getNews(): List<NewsModel>
}