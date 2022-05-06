package com.xenatronics.webagenda.presentation.screens.login

import com.xenatronics.webagenda.domain.model.Credentials

data class LoginState(
    val email:String="",
    val password:String="",
    val mailError:String?=null,
    val passwordError:String?=null
)






