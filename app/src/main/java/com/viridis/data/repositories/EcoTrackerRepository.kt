package com.viridis.data.repositories

import kotlinx.coroutines.flow.Flow

interface EcoTrackerRepository {

    fun postTrackerDate(id: String, payload: String): Flow<Any>

    fun fetchTrackerDate(id: String): Flow<Map<String, String>>
}