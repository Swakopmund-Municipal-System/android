package com.example.swakopmundapp.service.helpers

sealed class Status {
    object Success : Status()
    object Failure : Status()
    object TimedOut : Status()
}