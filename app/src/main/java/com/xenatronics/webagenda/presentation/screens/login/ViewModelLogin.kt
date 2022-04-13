package com.xenatronics.webagenda.presentation.screens.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.xenatronics.webagenda.common.events.LoginEvent
import com.xenatronics.webagenda.common.events.UIEvent
import com.xenatronics.webagenda.common.navigation.Screen
import com.xenatronics.webagenda.common.util.MessageLogin
import com.xenatronics.webagenda.common.util.StatusLogin
import com.xenatronics.webagenda.domain.model.User
import com.xenatronics.webagenda.domain.usecase.Login.UseCaseLogin
import com.xenatronics.webagenda.domain.usecase.checkLogin
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelLogin @Inject constructor(
    private val useCase: UseCaseLogin
) : ViewModel() {
    val nom = mutableStateOf("")
    val password = mutableStateOf("")
    private val _stateLogin = mutableStateOf(StatusLogin.None)
    val stateLogin: State<StatusLogin> get() = _stateLogin
    private val token = mutableStateOf("")
    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun OnEvent(event: LoginEvent) {

        when (event) {
            is LoginEvent.OnCheck -> {
                // verifie nom et password
                val res = checkLogin(User(nom.value, password.value))
                if (res == MessageLogin.OK) {
                    login(User(nom.value, password.value))
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
            is LoginEvent.OnSucces -> {
                // success on va sur la liste des rdv
                sendUIEvent(UIEvent.Navigate(Screen.ListRdvScreen.route))
            }
            is LoginEvent.OnFailed -> {
                sendUIEvent(UIEvent.ShowSnackBar("Ok", event.error))
            }
            else -> Unit
        }
    }

    private fun sendUIEvent(event: UIEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    private fun login(user: User) {
        viewModelScope.launch {
            kotlin.runCatching {
                useCase.login(user)
            }.onSuccess {
                if (it.Status.contains("incorrect")) {
                    OnEvent(LoginEvent.OnFailed(it.Status))
                }
                if (it.Status.contains("-")) {
                    OnEvent(LoginEvent.OnSucces)
                    token.value = it.Status
                }
            }.onFailure {
                token.value = ""
                OnEvent(LoginEvent.OnFailed("erreur internet"))
            }
        }
    }

    fun goToRdvList(navController: NavController) {
        viewModelScope.launch {
            navController.navigate(Screen.ListRdvScreen.route)
        }
    }
}
