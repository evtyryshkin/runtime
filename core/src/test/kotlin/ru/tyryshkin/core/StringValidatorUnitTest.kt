package ru.tyryshkin.core

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.tyryshkin.core.domain.TimeConverter

class StringValidatorUnitTest {

    @Test
    fun converting_time_toString_isCorrect() {
        val timeInSecond1 = 0L
        val expectedResult1 = "00:00:00"
        val actualResult1 = TimeConverter.convert(timeInSecond1)
        assertEquals(expectedResult1, actualResult1)

        val timeInSecond2 = 400L
        val expectedResult2 = "00:06:40"
        val actualResult2 = TimeConverter.convert(timeInSecond2)
        assertEquals(expectedResult2, actualResult2)

        val timeInSecond3 = 25407L
        val expectedResult3 = "07:03:27"
        val actualResult3 = TimeConverter.convert(timeInSecond3)
        assertEquals(expectedResult3, actualResult3)

        val timeInSecond4 = -10L
        val expectedResult4 = "00:00:00"
        val actualResult4 = TimeConverter.convert(timeInSecond4)
        assertEquals(expectedResult4, actualResult4)
    }
}
