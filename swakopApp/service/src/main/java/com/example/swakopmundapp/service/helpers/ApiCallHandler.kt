package com.example.swakopmundapp.service.helpers

import android.util.Log
import retrofit2.Response

const val DeveloperErrorLogger = "ERROR_LOGGER"

internal inline fun <T> safeApiCall(apiCall: () -> Response<T>): ServiceResponse<T> {

    val response: Response<T>

    try {
        response = apiCall.invoke()
    } catch (t: Throwable) {

        return if (t.localizedMessage == "timeout") {
            ServiceResponse.timedOut("Slow internet connection, might take longer")
        } else {
            Log.e(DeveloperErrorLogger, "com.example.service.remoteRepo 'ApiCallHandler()' -> Throwable: $t")
            ServiceResponse.failure("Something went wrong")
        }
    }

    return if (response.isSuccessful) {
        ServiceResponse.successWithData(response)
    } else {
        ServiceResponse.failure(customErrorGenerator(response.code()))
    }

}