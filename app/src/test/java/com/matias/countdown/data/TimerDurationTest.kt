package com.matias.countdown.data

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class TimerDurationTest {

    @Test
    internal fun `given a 0 duration then isSet is false`() {
        val sut = TimerDuration(0, 0, 0)

        assertFalse(sut.isSet())
    }

    @Test
    internal fun `given a 1 second duration then isSet is true`() {
        val sut = TimerDuration(0, 0, 1)

        assertTrue(sut.isSet())
    }

    @ParameterizedTest(name = "Given {0}h, {1}m, {2}s when toCondensedFormat then {3}")
    @CsvSource(
        "0,0,0,000000",
        "0,0,1,000001",
        "0,1,2,000102",
        "0,12,3,001203",
        "1,23,4,012304",
        "12,34,5,123405",
        "12,34,56,123456",
    )
    fun `test toCondensedFormat`(
        hours: Int,
        minutes: Int,
        seconds: Int,
        expectedResult: String
    ) {
        val sut = TimerDuration(hours, minutes, seconds)

        assertEquals(expectedResult, sut.toCondensedFormat())
    }

    @ParameterizedTest(name = "Given {0}h, {1}m, {2}s when toSeconds then {3}")
    @CsvSource(
        "0,0,0,0",
        "0,0,1,1",
        "0,1,2,62",
        "0,12,3,723",
        "1,23,4,4984",
    )
    fun `test toSeconds`(
        hours: Int,
        minutes: Int,
        seconds: Int,
        expectedResult: Int
    ) {
        val sut = TimerDuration(hours, minutes, seconds)

        assertEquals(expectedResult, sut.toSeconds())
    }
}
