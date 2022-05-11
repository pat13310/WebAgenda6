package com.xenatronics.webagenda.presentation.screens.register

import com.xenatronics.webagenda.domain.model.Credentials

data class RegisterState(
    val nom:String="",
    val email:String="",
    val repeatEmail:String="",
    val password:String="",
    val error:String?=null
)
