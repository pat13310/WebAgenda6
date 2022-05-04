package com.xenatronics.webagenda.common.events

sealed class LoginEvent {
    object OnSubmit : LoginEvent()
    object OnSucces : LoginEvent()
    data class OnFailed(val error: String) : LoginEvent()
    data class EmailChanged(val email: String) : LoginEvent()
    data class PasswordChanged(val password: String) : LoginEvent()
    object onNavigateRegister:LoginEvent()
    object onNavigateListRdv:LoginEvent()
}
