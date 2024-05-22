package com.viridis.data.repositories

import com.viridis.service.ApiService
import kotlinx.coroutines.flow.flow

class AirPollutionRepositoryImpl(private val service: ApiService) : AirPollutionRepository {

    override fun fetchAirPollutionData(keyword: String) = flow {
        emit(service.getAirPollutionData(keyword = keyword))
    }
}