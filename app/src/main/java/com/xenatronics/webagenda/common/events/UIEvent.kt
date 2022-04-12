package com.xenatronics.webagenda.common.events

sealed interface UIEvent{
    object PopBackStack: UIEvent
    data class Navigate(val route:String): UIEvent
    data class ShowSnackBar(val action:String, val message:String): UIEvent
}