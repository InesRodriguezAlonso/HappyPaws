package com.happypaws.utils

import android.telephony.PhoneNumberUtils
import android.text.TextUtils
import android.util.Patterns

class ValidationUtils {
    companion object {
        fun isValidEmail(email: String): Boolean {
            return if (TextUtils.isEmpty(email)) {
                false
            } else {
                val pattern = Patterns.EMAIL_ADDRESS
                pattern.matcher(email).matches()
            }
        }

        fun isValidPhoneNumber(phoneNumber: String): Boolean {
            return if (TextUtils.isEmpty(phoneNumber)) {
                false
            } else {
                PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber)
            }
        }
    }
}