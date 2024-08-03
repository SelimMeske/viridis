package com.viridis.ui.pollution

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.viridis.data.models.AirQualityModel
import com.viridis.data.repositories.AirPollutionRepository
import com.viridis.service.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class PollutionViewModel @Inject constructor(
    private val repository: AirPollutionRepository
) : ViewModel() {

    private val _pollutionState = MutableStateFlow(AirQualityModel())
    val pollutionState = _pollutionState.asStateFlow()

    private val _selectedFlag = MutableStateFlow(0)
    val selectedFlag = _selectedFlag.asStateFlow()

    private val _showProgressIndicator = MutableStateFlow(true)
    val showProgressIndicator = _showProgressIndicator.asStateFlow()

    init {
        fetchPollutionData(CountryKeywordEnum.GERMANY)
    }

    fun fetchPollutionData(country: CountryKeywordEnum) {
        viewModelScope.launch {
            repository.fetchAirPollutionData(country.value)
                .flowOn(Dispatchers.IO)
                .catch { /* Handle error */ }
                .collect {
                    _pollutionState.value = it
                    _showProgressIndicator.value = false
                }
        }
    }

    fun selectFlag(value: Int) {
        _showProgressIndicator.value = true
        _selectedFlag.value = value
    }
}