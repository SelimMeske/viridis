package com.viridis.ui.pollution

import com.viridis.R

enum class PollutionLevels(val color: Long, val level: Int) {
    UNDEFINED(0xFFFC9A58, R.string.undefined),
    GOOD(0xFFA4E061, R.string.good),
    MODERATE(0xFFFBD450, R.string.moderate),
    UNHEALTHY_FOR_SENSITIVE(0xFFFC9A58, R.string.unhealthy_for_sesitive),
    UNHEALTHY(0xFFFD6B71, R.string.unhealthy),
    VERY_UNHEALTHY(0xFFA97BBC, R.string.very_unhealthy);

    companion object {
        fun getColorByAQI(AQI: Int) = when(AQI) {
            in 0 .. 50 -> GOOD
            in 51 .. 100 -> MODERATE
            in 101 .. 150 -> UNHEALTHY_FOR_SENSITIVE
            in 151 .. 200 -> UNHEALTHY
            in 201 .. 50000 -> VERY_UNHEALTHY
            else -> UNDEFINED
        }
    }
}
