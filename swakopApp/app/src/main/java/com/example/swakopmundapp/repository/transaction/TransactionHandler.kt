package com.example.swakopmundapp.repository.transaction

/**
 * Consumed by Repository transaction and UI
 */
sealed class TransactionHandler<T>(val data: T? = null, val error: Throwable? = null) {

    class Started<T>(data: T? = null) : TransactionHandler<T>(data)
    class SuccessfullyCompleted<T>(data: T? = null) : TransactionHandler<T>(data)
    class Error<T>(data: T? = null) : TransactionHandler<T>(data)
    class PoorConnection<T>(data: T? = null) : TransactionHandler<T>(data)

}