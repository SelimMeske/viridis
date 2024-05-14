package com.viridis.ui.eco_tracker

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.viridis.data.repositories.EcoTrackerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

@HiltViewModel
class EcoTrackerViewModel @Inject constructor(
    private val ecoTrackerRepository: EcoTrackerRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val memberId = checkNotNull(savedStateHandle.get<String>("memberId"))

    private val _dateState = MutableStateFlow(mutableStateListOf<String>())
    val dateState = _dateState.asStateFlow()

    private val _checkInButtonState = MutableStateFlow(false)
    val checkInButtonState = _checkInButtonState.asStateFlow()

    private val germanTimeZone: TimeZone = TimeZone.getTimeZone("Europe/Berlin")
    private val calendar: Calendar = Calendar.getInstance(germanTimeZone)
    private val today = formatDate(calendar)

    init {
        getTrackedDates()
    }

    fun getUpcoming7Days(): MutableList<TrackerData> {
        calendar.firstDayOfWeek = Calendar.MONDAY

        // Set the calendar to the current date
        calendar.time = Date()

        // Move the calendar to the start of the week (Sunday)
        calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)

        val dateList = mutableListOf<TrackerData>()
        for (i in 0 until 7) {
            val date = calendar.time
            println(formatDate(date))
            dateList.add(formatDate(date))
            calendar.add(Calendar.DATE, 1)
        }
        return dateList
    }

    private fun getTrackedDates() {
        viewModelScope.launch {
            ecoTrackerRepository.fetchTrackerDate(memberId)
                .flowOn(Dispatchers.IO)
                .catch { /* Handle error */ }
                .collect {
                    val datesList = mutableStateListOf<String>()
                    _checkInButtonState.value = it.values.contains(today)
                    it.values.forEach { date ->
                        datesList.add(date)
                    }
                    _dateState.value = datesList
                }
        }
    }

    fun setTrackedDate() {
        viewModelScope.launch {
            ecoTrackerRepository.postTrackerDate(memberId, today)
                .flowOn(Dispatchers.IO)
                .catch { /* Handle error */ }
                .collect {
                    // Pull the latest data to refresh the state
                    getTrackedDates()
                }
        }
    }
}

private fun formatDate(date: Date): TrackerData {
    val day = SimpleDateFormat("dd", Locale.getDefault())
    val stringDate = SimpleDateFormat("dd/MM/yy", Locale.getDefault()).format(date)
    val dayName = SimpleDateFormat("EEEE", Locale.GERMAN)
    return TrackerData(dayName.format(date), day.format(date), stringDate)
}

private fun formatDate(calendar: Calendar): String {
    val sdf = SimpleDateFormat("dd/MM/yy", Locale.getDefault())
    return sdf.format(calendar.time)
}

data class TrackerData(val dayName: String, val day: String, val stringDate: String)