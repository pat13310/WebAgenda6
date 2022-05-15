package com.xenatronics.webagenda.presentation.screens.login

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xenatronics.webagenda.common.events.LoginEvent
import com.xenatronics.webagenda.common.events.UIEvent
import com.xenatronics.webagenda.common.navigation.Screen
import com.xenatronics.webagenda.common.util.MessageLogin
import com.xenatronics.webagenda.common.util.StatusLogin
import com.xenatronics.webagenda.domain.model.Credentials
import com.xenatronics.webagenda.domain.usecase.login.UseCaseLogin
import com.xenatronics.webagenda.domain.usecase.login.checkLogin
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelLogin @Inject constructor(
    private val useCase: UseCaseLogin,

    ) : ViewModel() {


    private val _stateLogin = mutableStateOf(StatusLogin.None)
    val stateLogin: State<StatusLogin> get() = _stateLogin

    private val token = mutableStateOf("")

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var state by mutableStateOf(LoginState())

    fun onEvent(event: LoginEvent) {

        when (event) {
            is LoginEvent.onNavigateListRdv -> {
                sendUIEvent(UIEvent.Navigate(Screen.ListRdvScreen.route))
            }
            is LoginEvent.onNavigateRegister -> {
                sendUIEvent(UIEvent.Navigate(Screen.RegisterScreen.route))
            }
            is LoginEvent.EmailChanged -> {
                state = state.copy(email = event.email)
            }
            is LoginEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
            }

            is LoginEvent.OnSubmit -> {
                val res = checkLogin(Credentials(state.email, state.password))
                if (res == MessageLogin.OK) {
                    login(Credentials(state.email, state.password))
                } else {
                    var errorMessage = ""
                    when (res) {
                        MessageLogin.NOM_EMPTY -> errorMessage = "Le nom du login est vide"
                        MessageLogin.MAIL_INCORRECT -> errorMessage = "Le mail est incorrect"
                        MessageLogin.PASS_EMPTY -> errorMessage = "Le mot de passe est vide"
                        MessageLogin.MAIL_EMPTY -> errorMessage = "Le mail est vide"
                        MessageLogin.PASS_INCORRECT -> errorMessage =
                            "Le mot de passe est incorrect"
                        else -> {}
                    }
                    sendUIEvent(UIEvent.ShowSnackBar("OK", errorMessage))
                }
            }
            is LoginEvent.OnSuccess -> {
                // success on va sur la liste des rdv
                sendUIEvent(UIEvent.Navigate(Screen.ListRdvScreen.route))
            }
            is LoginEvent.OnFailed -> {
                sendUIEvent(UIEvent.ShowSnackBar("Ok", event.error))
            }
        }
    }

    private fun sendUIEvent(event: UIEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    private fun login(user: Credentials) {
        viewModelScope.launch {
            kotlin.runCatching {
                useCase.login(user)
            }.onSuccess {
                if (it.Status.contains("incorrect")) {
                    onEvent(LoginEvent.OnFailed(it.Status))
                }
                if (it.Status.contains("-")) {
                    onEvent(LoginEvent.OnSuccess)
                    token.value = it.Status
                }
            }.onFailure {
                token.value = ""
                onEvent(LoginEvent.OnFailed("erreur internet"))
            }
        }
    }
}
