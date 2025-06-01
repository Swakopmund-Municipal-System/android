package com.example.swakopmundapp.repository.transaction

import com.example.swakopmundapp.service.helpers.ServiceResponse
import com.example.swakopmundapp.service.helpers.Status
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

inline fun <T : ServiceResponse<N>, N> fetchSaveTransaction(
    crossinline fetchFromApi: suspend () -> T,
    crossinline save: suspend (T) -> Unit
) = flow {

    emit(TransactionHandler.Started("true"))

    val response = fetchFromApi()

    when (response.status) {

        is Status.Success ->{
            save(response)
            delay(2_000)
            emit(TransactionHandler.SuccessfullyCompleted("true"))
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
