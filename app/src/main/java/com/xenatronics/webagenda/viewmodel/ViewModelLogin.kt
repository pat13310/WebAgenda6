package com.xenatronics.webagenda.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.xenatronics.webagenda.data.ResponseSimple
import com.xenatronics.webagenda.data.User
import com.xenatronics.webagenda.navigation.Screen
import com.xenatronics.webagenda.repository.RepositoryLogin
import com.xenatronics.webagenda.util.StatusLogin
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelLogin @Inject constructor() : ViewModel() {
    val nom = mutableStateOf("")
    val password = mutableStateOf("")
    private val _stateLogin = mutableStateOf(StatusLogin.None)
    val stateLogin: State<StatusLogin> get()= _stateLogin
    private val token = mutableStateOf("")

    fun login(user: User) {
        viewModelScope.launch {
            kotlin.runCatching {
                RepositoryLogin.login(user = user)
            }.onSuccess {
                //ResponseSimple(it.Status)
                if (it.Status.contains("login")) {
                    Log.d("Loginmodel", "failure")
                    _stateLogin.value = StatusLogin.Failure

                }
                if (it.Status.contains("-")) {
                    Log.d("Loginmodel", "OK")
                    _stateLogin.value = StatusLogin.Ok
                    //_stateLogin.emit(StatusLogin.Ok)
                    token.value = it.Status
                }
            }.onFailure {
                //ResponseSimple("")
                token.value = ""
                _stateLogin.value = StatusLogin.Failure
            }
        }
    }

    fun goToRdvList(navController: NavController){
        viewModelScope.launch {
            navController.navigate(Screen.ListRdvScreen.route)
        }
    }
}

sealed class LoginEvent {
    data class OnStatus(val statusLogin: StatusLogin) : LoginEvent()
    object OnValidate: LoginEvent()
    object OnStart : LoginEvent()
    object OnStop : LoginEvent()
}