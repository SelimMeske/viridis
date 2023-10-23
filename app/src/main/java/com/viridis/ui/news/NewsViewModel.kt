package com.viridis.ui.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.viridis.api.ApiService
import com.viridis.ui.news.model.NewsModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {

    private val _newsState = MutableStateFlow(listOf<NewsModel>())
    val newsState = _newsState.asStateFlow()

    fun fetchNews() {
        viewModelScope.launch {
            try {
                val data = apiService.getNews().body() ?: emptyList()
                _newsState.value = data
            } catch (e: Exception) {
                _newsState.value = emptyList()
            }
        }
    }
}