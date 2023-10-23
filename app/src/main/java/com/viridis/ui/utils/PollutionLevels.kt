package com.viridis.ui.utils

enum class PollutionLevels(val color: Long, val level: String) {
    UNDEFINED(0xFFFC9A58, "Nicht definiert"),
    GOOD(0xFFA4E061, "Gut"),
    MODERATE(0xFFFBD450, "Mäßig"),
    UNHEALTHY_FOR_SENSITIVE(0xFFFC9A58, "Ungesund für sensible Gruppen"),
    UNHEALTHY(0xFFFD6B71, "Ungesund"),
    VERY_UNHEALTHY(0xFFA97BBC, "Sehr Ungesund");

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
