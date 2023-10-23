package com.viridis.ui.polution_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.viridis.api.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class PollutionViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {

    private val _pollutionState = MutableStateFlow(AirQualityData())
    val pollutionState = _pollutionState.asStateFlow()

    private val _selectedFlag = MutableStateFlow(0)
    val selectedFlag = _selectedFlag.asStateFlow()

    init {
        fetchPollutionData(CountryKeywordEnum.GERMANY)
    }

    fun fetchPollutionData(country: CountryKeywordEnum) {
        viewModelScope.launch {
            try {
                val data = apiService.getAirPollutionData(keyword = country.value).body() ?: AirQualityData()
                _pollutionState.value = data
            } catch (e: Exception) {
                _pollutionState.value = AirQualityData()
            }
        }
    }

    fun selectFlag(value: Int) {
        _selectedFlag.value = value
    }
}