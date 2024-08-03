package com.viridis.ui.news

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.viridis.data.models.NewsModel
import com.viridis.data.repositories.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    private val _newsState = MutableStateFlow(listOf<NewsModel>())
    val newsState = _newsState.asStateFlow()

    private var _showProgressIndicator = MutableStateFlow(true)
    val showProgressIndicator = _showProgressIndicator.asStateFlow()

    fun fetchNews() {
        viewModelScope.launch {
            newsRepository.getNews()
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    _newsState.value = emptyList()
                    Log.e("ViridisError: ", e.localizedMessage ?: "")
                }
                .collect {
                    _newsState.value = it
                    _showProgressIndicator.value = false
                }
        }
    }
}