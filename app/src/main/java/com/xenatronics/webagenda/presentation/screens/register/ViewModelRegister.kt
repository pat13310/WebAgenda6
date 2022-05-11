package com.xenatronics.webagenda.presentation.screens.register

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xenatronics.webagenda.R
import com.xenatronics.webagenda.common.events.RegisterEvent
import com.xenatronics.webagenda.common.events.UIEvent
import com.xenatronics.webagenda.common.navigation.Screen
import com.xenatronics.webagenda.domain.model.Credentials
import com.xenatronics.webagenda.domain.usecase.ResultUseCase
import com.xenatronics.webagenda.domain.usecase.login.UseCaseLogin
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelRegister @Inject constructor(
    private val useCase: UseCaseLogin,
) : ViewModel() {

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    var state by mutableStateOf(RegisterState())

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.OnNavigateLogin -> {
                sendUIEvent(UIEvent.Navigate(Screen.LoginScreen.route))
            }
            is RegisterEvent.NameChanged -> {
                state = state.copy(nom = event.name)
            }
            is RegisterEvent.EmailChanged -> {
                state = state.copy(email = event.email)
            }
            is RegisterEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
            }
            is RegisterEvent.OnSubmit -> {
                val resultName: ResultUseCase = useCase.validateName.execute(state.nom)
                val resultMail: ResultUseCase = useCase.validateMail.execute(state.email)
                val resultPassword: ResultUseCase = useCase.validatePassword.execute(state.password)

                if (resultName != ResultUseCase.OK) {
                    sendUIEvent(UIEvent.ShowErrorMessage(resultName, focus = "name"))
                } else if (resultMail != ResultUseCase.OK) {
                    sendUIEvent(UIEvent.ShowErrorMessage(resultMail, focus="mail"))
                } else if (resultPassword != ResultUseCase.OK) {
                    sendUIEvent(UIEvent.ShowErrorMessage(resultPassword, focus="password"))
                }else{
                    register()
                }
            }
            else -> Unit
        }
    }

    private fun register() {
        viewModelScope.launch {
            kotlin.runCatching {
                useCase.register(
                    Credentials(
                        mail = state.email,
                        name = state.nom,
                        password = state.password
                    )
                )
            }.onSuccess {
                sendUIEvent(UIEvent.Navigate(Screen.ListRdvScreen.route))
            }.onFailure {
                sendUIEvent(UIEvent.ShowErrorMessage(ResultUseCase.InternetFailure))
            }
        }
    }

    private fun sendUIEvent(event: UIEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}