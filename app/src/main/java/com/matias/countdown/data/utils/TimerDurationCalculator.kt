package com.matias.countdown.data.utils

import com.matias.countdown.data.TimerDuration

class TimerDurationCalculator(private val maxLength: Int = TimerDuration.MAX_LENGTH) {

    fun appendDigit(value: Int, duration: TimerDuration): TimerDuration {
        val oldDuration = duration.toCondensedFormat()

        if (oldDuration.trimStart { it == '0' }.length >= maxLength) {
            throw DurationAppendException()
        } else {
            val durations = (oldDuration + value.toString())
                .removePrefix("0")
                .padStart(maxLength, '0')
                .chunked(2)
            val hours = durations[0].toInt()
            val minutes = durations[1].toInt()
            val seconds = durations[2].toInt()

            return TimerDuration(hours, minutes, seconds)
        }
    }

    fun removeLastDigit(duration: TimerDuration): TimerDuration {
        if (!duration.isSet()) {
            throw DurationRemoveException()
        } else {
            val oldDuration = duration.toCondensedFormat()
            val durations = oldDuration
                .substring(0, oldDuration.length - 1)
                .padStart(TimerDuration.MAX_LENGTH, '0')
                .chunked(2)
            val hours = durations[0].toInt()
            val minutes = durations[1].toInt()
            val seconds = durations[2].toInt()

            return TimerDuration(hours, minutes, seconds)
        }
    }

    class DurationAppendException : RuntimeException()
    class DurationRemoveException : RuntimeException()
}
