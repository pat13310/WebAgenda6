package com.xenatronics.webagenda.common.events

import com.xenatronics.webagenda.common.util.StatusLogin

sealed class LoginEvent {
    data class OnStatus(val statusLogin: StatusLogin) : LoginEvent()
    object OnValidate: LoginEvent()
    object OnCheck:LoginEvent()
    object OnSucces : LoginEvent()
    data class OnFailed(val error:String) : LoginEvent()
}
