package com.xenatronics.webagenda.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xenatronics.webagenda.data.ResponseSimple
import com.xenatronics.webagenda.data.User
import com.xenatronics.webagenda.repository.RepositoryLogin
import com.xenatronics.webagenda.util.StatusLogin
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelLogin @Inject constructor() : ViewModel() {
    val nom = mutableStateOf<String>("")
    val password = mutableStateOf<String>("")
    private val _stateLogin = MutableStateFlow(StatusLogin.None)
    val stateLogin: StateFlow<StatusLogin> = _stateLogin

    private val token = mutableStateOf("")

    fun login(user: User) {
        viewModelScope.launch {
            kotlin.runCatching {
                RepositoryLogin.login(user = user)
            }.onSuccess {
                ResponseSimple(it.status)
                _stateLogin.value=StatusLogin.Ok
                token.value=it.status
            }.onFailure {
                ResponseSimple("")
                token.value=""
                _stateLogin.value=StatusLogin.Failure
            }
        }
    }
}

