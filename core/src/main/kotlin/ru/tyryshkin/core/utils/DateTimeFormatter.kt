package ru.tyryshkin.core.utils

import java.time.ZoneId
import java.time.format.DateTimeFormatter

object DateTimeFormatter {

    val DATE_TIME: DateTimeFormatter =
        DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm").withZone(ZoneId.systemDefault())
}
