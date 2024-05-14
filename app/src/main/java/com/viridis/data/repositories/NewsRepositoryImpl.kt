package com.viridis.data.repositories

import com.viridis.data.models.NewsModel
import com.viridis.service.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NewsRepositoryImpl(private val service: ApiService) : NewsRepository {

    override fun getNews(): Flow<List<NewsModel>> = flow {
        emit(service.getNews())
    }
}