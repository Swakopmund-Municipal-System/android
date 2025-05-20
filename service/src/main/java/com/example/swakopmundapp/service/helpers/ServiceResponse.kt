package com.example.swakopmundapp.service.helpers

import retrofit2.Response

data class ServiceResponse<T>(
    val status: Status,
    val data: Response<T>?,
    val error: String?,
    val timeOut: String?
) {
    companion object {
        fun <T> successNotifier(): ServiceResponse<T> {
            return ServiceResponse(
                status = Status.Success,
                data = null,
                error = null,
                timeOut = null
            )
        }

        fun <T>  successWithData(data: Response<T>): ServiceResponse<T> {
            return ServiceResponse(
                status = Status.Success,
                data = data,
                error = null,
                timeOut = null
            )
        }

        fun <T>  failure(error: String): ServiceResponse<T> {
            return ServiceResponse(
                status = Status.Failure,
                data = null,
                error = error,
                timeOut = null
            )
        }

        fun <T>  timedOut(timeOut: String): ServiceResponse<T> {
            return ServiceResponse(
                status = Status.TimedOut,
                data = null,
                error = null,
                timeOut = timeOut
            )
        }
    }

    val failed: Boolean
        get() = this.status == Status.Failure

    val body: T
        get() = this.data!!.body()!!
}
