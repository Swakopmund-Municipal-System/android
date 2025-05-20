package com.example.swakopmundapp.service.helpers

import android.util.Log

internal fun customErrorGenerator(code: Int): String {
    when (code) {

        400 -> {
            return "Bad request"
        }

        401 -> {
            return "Unauthorised"
        }

        403 -> {
            return "Forbidden"
        }

        404 -> {
            return "Service is unavailable"
        }

        408 -> {
            return "Your internet connection is slow, this might take longer than usual"
        }

        409 -> {
            return "This account already exist."
        }

        410 -> {
            return "Service is unavailable"
        }

        500 -> {
            return "Internal server error"
        }

        503 -> {
            return "Service unavailable"
        }

        else -> {
            Log.e(DeveloperErrorLogger, "DeveloperErrorLogger -> Code: $code | message: Something went wrong")
            return "Something went wrong"
        }
    }
}