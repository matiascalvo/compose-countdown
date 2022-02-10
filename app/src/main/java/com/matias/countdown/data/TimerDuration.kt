package com.matias.countdown.data

import kotlin.math.roundToInt

private const val SECONDS_IN_MINUTES = 60
private const val MINUTES_IN_HOURS = SECONDS_IN_MINUTES
private const val SECONDS_IN_HOURS = 3600
private const val MILLISECONDS_IN_SECOND = 1_000f

data class TimerDuration(
    val hours: Int = 0,
    val minutes: Int = 0,
    val seconds: Int = 0,
) {
    fun isSet() = hours != 0 || minutes != 0 || seconds != 0
    fun toCondensedFormat(): String {
        return hours.toString().padStart(2, '0') +
            minutes.toString().padStart(2, '0') +
            seconds.toString().padStart(2, '0')
    }

    fun toSeconds() =
        hours * MINUTES_IN_HOURS * SECONDS_IN_MINUTES + minutes * SECONDS_IN_MINUTES + seconds

    companion object {
        const val MAX_LENGTH = 6

        fun fromMilliSeconds(value: Long): TimerDuration {
            val secondsUntilFinished = (value / MILLISECONDS_IN_SECOND).roundToInt()
            val seconds = secondsUntilFinished % SECONDS_IN_MINUTES
            val minutes = secondsUntilFinished / SECONDS_IN_MINUTES % MINUTES_IN_HOURS
            val hours = secondsUntilFinished / (SECONDS_IN_HOURS)

            return TimerDuration(hours, minutes, seconds)
        }
    }
}
