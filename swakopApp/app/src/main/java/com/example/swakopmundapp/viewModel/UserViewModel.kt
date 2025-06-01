package com.example.swakopmundapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.swakopmundapp.repository.UserRepository
import com.example.swakopmundapp.repository.transaction.TransactionHandler
import com.example.swakopmundapp.util.clearSharedPreference
import kotlinx.coroutines.Dispatchers

class UserViewModel(
    private val repo: UserRepository
) : ViewModel() {

    fun logout(): LiveData<TransactionHandler<out Any?>> =
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            repo.logout().collect {
                emit(it)
            }
        }

    fun clearServiceInterceptor() {
        repo.clearServiceInterceptor()
        clearSharedPreference()
    }

}