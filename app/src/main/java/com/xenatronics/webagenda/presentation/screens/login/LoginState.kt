package com.xenatronics.webagenda.presentation.screens.login

import com.xenatronics.webagenda.domain.model.Credentials

data class LoginState(
    var email:String="",
    var password:String="",
    val mailError:String?=null,
    val passwordError:String?=null
)






