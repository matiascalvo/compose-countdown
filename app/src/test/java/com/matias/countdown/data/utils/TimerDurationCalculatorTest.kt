package com.matias.countdown.data.utils

import com.matias.countdown.data.TimerDuration
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

private val EMPTY_DURATION = TimerDuration(0, 0, 0)
private const val DEFAULT_MAX_LENGTH = 6
private const val REDUCED_MAX_LENGTH = 4

internal class TimerDurationCalculatorTest {

    @Nested
    inner class AppendDigitTests {
        @Nested
        inner class GivenDefaultAsMaxLength {

            private val sut = TimerDurationCalculator(DEFAULT_MAX_LENGTH)

            @ParameterizedTest(name = "Given {0}h, {1}m, {2}s when appendDigit {3} then {4}")
            @CsvSource(
                "0,0,0,1,000001",
                "0,0,1,2,000012",
                "0,0,12,3,000123",
                "0,1,23,4,001234",
                "0,12,34,5,012345",
                "1,23,45,6,123456",
            )
            fun `test digit append`(
                hours: Int,
                minutes: Int,
                seconds: Int,
                numberToAppend: Int,
                expectedResult: String
            ) {
                val output = sut.appendDigit(numberToAppend, TimerDuration(hours, minutes, seconds))

                assertEquals(expectedResult, output.toCondensedFormat())
            }

            @Test
            internal fun `given 12h 34m 56s when appending 7 then exception is thrown`() {
                assertThrows<TimerDurationCalculator.DurationAppendException> {
                    sut.appendDigit(
                        7,
                        TimerDuration(12, 34, 56)
                    )
                }
            }
        }

        @Nested
        inner class Given2AsMaxLength {
            private val sut = TimerDurationCalculator(2)

            @Test
            internal fun `given 11 seconds when appending 1 then exception is thrown`() {
                assertThrows<TimerDurationCalculator.DurationAppendException>() {
                    sut.appendDigit(
                        1,
                        TimerDuration(0, 0, 11)
                    )
                }
            }
        }

        @Nested
        inner class Given4AsMaxLength {
            private val sut = TimerDurationCalculator(REDUCED_MAX_LENGTH)

            @Test
            internal fun `given 12 minutes 34 seconds when appending 5 then exception is thrown`() {
                assertThrows<TimerDurationCalculator.DurationAppendException> {
                    sut.appendDigit(
                        5,
                        TimerDuration(0, 12, 34)
                    )
                }
            }
        }
    }

    @Nested
    inner class RemoveLastDigitTests {

        private val sut = TimerDurationCalculator(REDUCED_MAX_LENGTH)

        @Test
        fun `given empty duration when removingLastDigit then 1 second is returned`() {
            assertThrows<TimerDurationCalculator.DurationRemoveException> {
                sut.removeLastDigit(EMPTY_DURATION)
            }
        }

        @ParameterizedTest(name = "Given {0}h, {1}m, {2}s when removeLastDigit then {3}")
        @CsvSource(
            "0,0,1,000000",
            "0,0,12,000001",
            "0,1,23,000012",
            "0,12,34,000123",
            "1,23,45,001234",
            "12,34,56,012345",
        )
        fun `test removeLastDigit`(hours: Int, minutes: Int, seconds: Int, expectedResult: String) {
            val output = sut.removeLastDigit(TimerDuration(hours, minutes, seconds))

            assertEquals(expectedResult, output.toCondensedFormat())
        }
    }
}
