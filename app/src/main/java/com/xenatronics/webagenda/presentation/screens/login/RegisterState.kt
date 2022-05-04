package com.xenatronics.webagenda.presentation.screens.login

data class RegisterState(
    val email:String,
    val password:String,
    val emailError: String?=null,
    val passwordError: String?=null,
)
