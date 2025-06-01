package com.example.swakopmundapp.repository.transaction

import com.example.swakopmundapp.service.helpers.ServiceResponse
import kotlinx.coroutines.flow.flow
import com.example.swakopmundapp.service.helpers.Status

inline fun <T : ServiceResponse<N>, N> fetchFromApiTransaction(
    crossinline fetchFromApi: suspend () -> T
) = flow {

    emit(TransactionHandler.Started("true"))

    val response = fetchFromApi()

    when (response.status) {

        is Status.Success ->{
            emit(TransactionHandler.SuccessfullyCompleted(response.body))
        }

        is Status.TimedOut ->{
            emit(TransactionHandler.PoorConnection(response.timeOut))
        }

        is Status.Failure ->{
            emit(TransactionHandler.Error(response.error))
        }

        else -> {
            print(response.status)
        }
    }

}
