package francisco.simon.projectkmp.ui.utils

import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Duration.Companion.seconds
import kotlin.time.Instant

fun String.formatTime(): String {
    val instant = Instant.parse(this)
    val local = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    return "${local.date.day}.${local.date.month.number}.${local.date.year}"
}

fun Long.toHoursFromSeconds(): String {
    val hours = this.seconds.inWholeHours
    return "$hours ${if (hours == 1L) "Hour" else "Hours"}"
}
