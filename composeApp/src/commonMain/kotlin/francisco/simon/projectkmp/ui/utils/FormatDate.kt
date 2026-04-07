package francisco.simon.projectkmp.ui.utils

import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant

fun String.formatTime(): String {
    val instant = Instant.parse(this)
    val local = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    return "${local.date.day}.${local.date.month.number}.${local.date.year}"
}
