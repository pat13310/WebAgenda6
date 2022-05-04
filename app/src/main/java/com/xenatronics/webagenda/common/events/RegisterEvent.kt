package com.xenatronics.webagenda.common.events

sealed class RegisterEvent
{
    object OnSubmit : RegisterEvent()
    data class EmailChanged(val email: String) : RegisterEvent()
    data class PasswordChanged(val password: String) : RegisterEvent()
    data class RepeatEmailChanged(val password: String) : RegisterEvent()
    object OnNavigateLogin:RegisterEvent()
}

