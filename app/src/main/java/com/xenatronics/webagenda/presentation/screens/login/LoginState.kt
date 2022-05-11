package com.xenatronics.webagenda.presentation.screens.login

data class LoginState(
    var email: String = "",
    var password: String = "",
    val mailError: String? = null,
    val passwordError: String? = null
)






