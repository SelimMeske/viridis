package com.viridis.api

import com.viridis.api.news.AirPollutionService
import com.viridis.api.news.NewsService

interface ApiService : NewsService, AirPollutionService