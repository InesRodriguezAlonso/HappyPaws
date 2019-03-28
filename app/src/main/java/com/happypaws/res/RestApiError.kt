package com.happypaws.res

import android.text.TextUtils
import android.util.Log
import com.google.gson.Gson
import com.happypaws.res.model.ApiResponse
import retrofit2.adapter.rxjava.HttpException
import java.io.IOException

class RestApiError(private val error: Throwable?) : Throwable(error) {

    val appErrorMessage: String
        get() {
            if (this.error is IOException) {
                return restApiErrorMessage
            }
            if (this.error !is HttpException) {
                return defaultErrorMessage
            }

            val response = this.error.response()

            val status = getJsonStringFromResponse(response!!)
            if (!TextUtils.isEmpty(status!!)) {
                return status
            }

            val headers = response.headers().toMultimap()
            if (headers.containsKey(errorMessageHeader)) {
                return headers[errorMessageHeader]!![0]
            }

            return defaultErrorMessage
        }

    init {

        Log.e(TAG, "RestApiError: " + error!!.localizedMessage)
    }

    private fun getJsonStringFromResponse(response: retrofit2.Response<*>): String? {
        return try {
            val jsonString = response.errorBody()!!.string()
            val errorResponse = Gson().fromJson(jsonString, ApiResponse::class.java)
            errorResponse.status
        } catch (e: Exception) {
            null
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null || javaClass != other.javaClass) {
            return false
        }

        val that = other as RestApiError?

        return if (error != null) error == that!!.error else that!!.error == null
    }

    override fun hashCode(): Int {
        return error?.hashCode() ?: 0
    }

    companion object {
        var TAG = "RestApiError"

        var defaultErrorMessage = "Something went wrong, please try again later."
        var restApiErrorMessage = "No internet connection."
        var errorMessageHeader = "Error message."
    }
}
