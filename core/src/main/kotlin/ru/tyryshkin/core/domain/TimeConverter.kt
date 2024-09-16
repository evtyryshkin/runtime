package ru.tyryshkin.core.domain

import java.util.Locale

object TimeConverter {
    fun convert(time: Long): String {
        val correctTime = if (time < 0) 0L else time
        val hours = correctTime % 86400 / 3600
        val minutes = correctTime % 86400 % 3600 / 60
        val seconds = correctTime % 86400 % 3600 % 60
        return makeTimeString(hours, minutes, seconds)
    }

    private fun makeTimeString(hours: Long, minutes: Long, seconds: Long): String {
        return String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds)
    }
}
