package com.viridis.data.repositories

import com.viridis.service.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class EcoTrackerRepositoryImpl(private val service: ApiService) : EcoTrackerRepository {

    override fun postTrackerDate(id: String, payload: String) = flow {
        emit(service.postTrackerDate(id, payload))
    }

    override fun fetchTrackerDate(id: String): Flow<Map<String, String>> = flow {
        emit(service.getTrackerDate(id))
    }
}