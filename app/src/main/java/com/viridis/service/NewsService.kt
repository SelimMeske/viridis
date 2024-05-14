package com.viridis.service

import com.viridis.data.models.NewsModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface NewsService {

    @GET("news.json")
    suspend fun getNews(): List<NewsModel>
}