package com.happypaws.utils


import android.content.Context
import androidx.annotation.NonNull
import com.happypaws.R
import java.text.DateFormat
import java.util.*

class TimeUtils {
    companion object {
        fun getDateFormat(@NonNull dateMs: Long?, context: Context): String {
            return if (dateMs == null) {
                context.getString(R.string.activity_pet_profile_no_registration_date)
            } else DateFormat.getDateInstance(DateFormat.SHORT).format(Date(Objects.requireNonNull(dateMs)))

        }

        fun getFormattedAge(petAgeMonths: Long?, context: Context): String {
            if (petAgeMonths == null) {
                return context.applicationContext.getString(R.string.activity_pet_profile_no_age)
            }

            val years = getYears(petAgeMonths)
            val months = getMonths(petAgeMonths)

            val stringBuilder = StringBuilder()
            if (years != 0) {
                stringBuilder.append(context.applicationContext.resources.getQuantityString(R.plurals.activity_pet_profile_age_formatted_years, years, years))
            }
            if (months != 0) {
                stringBuilder.append(context.resources.getQuantityString(R.plurals.activity_pet_profile_age_formatted_months, months, months))
            }

            return stringBuilder.toString()
        }

        fun getYears(months: Long?): Int {
            return (months!! / 12).toInt()
        }

        fun getMonths(months: Long?): Int {
            return (months!! % 12).toInt()
        }
    }
}