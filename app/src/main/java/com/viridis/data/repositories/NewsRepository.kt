package com.viridis.data.repositories

import com.viridis.data.models.NewsModel
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    fun getNews(): Flow<List<NewsModel>>
}