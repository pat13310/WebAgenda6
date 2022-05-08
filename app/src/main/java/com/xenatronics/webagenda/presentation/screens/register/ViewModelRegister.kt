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
    @ApplicationContext val context: Context,
) : ViewModel() {

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var state by mutableStateOf(RegisterState())

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.OnNavigateLogin -> {
                sendUIEvent(UIEvent.Navigate(Screen.LoginScreen.route))
            }
            is RegisterEvent.EmailChanged -> {
                state = state.copy(email = event.email)
            }
            is RegisterEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
            }
            is RegisterEvent.NameChanged -> {
                state = state.copy(nom = event.nom)
            }
            is RegisterEvent.OnSubmit -> {
                register(context = context)
            }
            else -> Unit
        }
    }

    private fun register(context: Context) {
        viewModelScope.launch {
            kotlin.runCatching {
                useCase.register(
                    Credentials(
                        name = state.nom,
                        mail = state.email,
                        password = state.password
                    )
                )
            }.onSuccess {
                sendUIEvent(UIEvent.Navigate(Screen.ListRdvScreen.route))
            }.onFailure {
                sendUIEvent(UIEvent.ShowSnackBar("", context.getString(R.string.FailedInternet)))
            }
        }
    }

    private fun sendUIEvent(event: UIEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}