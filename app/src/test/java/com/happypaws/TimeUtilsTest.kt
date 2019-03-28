package com.happypaws

import android.content.Context
import com.happypaws.utils.TimeUtils
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TimeUtilsTest {

    @Mock
    private lateinit var mockApplicationContext: Context

    @Test
    fun addition_getDateFormat() {
        val dateMs = 1379887200000L

        assertEquals(TimeUtils.getDateFormat(dateMs, mockApplicationContext), "23/09/13")

        `when`(mockApplicationContext.getString(R.string.activity_pet_profile_no_registration_date)).thenReturn("--/--/--")
        assertEquals(TimeUtils.getDateFormat(null, mockApplicationContext), "--/--/--")
    }

    @Test
    fun addition_getYears() {
        var months: Long = 0
        assertEquals(TimeUtils.getYears(months).toLong(), 0)

        months = 12
        assertEquals(TimeUtils.getYears(months).toLong(), 1)

        months = 24
        assertEquals(TimeUtils.getYears(months).toLong(), 2)

        months = 13
        assertEquals(TimeUtils.getYears(months).toLong(), 1)

        months = 40
        assertEquals(TimeUtils.getYears(months).toLong(), 3)
    }

    @Test
    fun addition_getMonths() {
        var months: Long = 0
        assertEquals(TimeUtils.getMonths(months).toLong(), 0)

        months = 12
        assertEquals(TimeUtils.getMonths(months).toLong(), 0)

        months = 14
        assertEquals(TimeUtils.getMonths(months).toLong(), 2)

        months = 23
        assertEquals(TimeUtils.getMonths(months).toLong(), 11)

        months = 37
        assertEquals(TimeUtils.getMonths(months).toLong(), 1)
    }
}