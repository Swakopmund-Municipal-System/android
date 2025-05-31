package com.example.swakopmundapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.swakopmundapp.core.dto.LoginDto
import com.example.swakopmundapp.core.dto.SignUpDto
import com.example.swakopmundapp.repository.AuthenticationRepository
import com.example.swakopmundapp.repository.transaction.TransactionHandler
import kotlinx.coroutines.Dispatchers

class AuthenticationViewModel(
    private val repo: AuthenticationRepository
) : ViewModel() {

    fun signup(signup: SignUpDto): LiveData<TransactionHandler<out Any?>> =
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            repo.signup(signup).collect {
                emit(it)
            }
        }

    fun login(login: LoginDto): LiveData<TransactionHandler<out Any?>> =
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            repo.login(login).collect {
                emit(it)
            }
        }

}