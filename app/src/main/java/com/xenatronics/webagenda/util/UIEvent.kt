package com.xenatronics.webagenda.util

sealed interface UIEvent{
    object PopBackStack:UIEvent
    data class Navigate(val route:String):UIEvent
    data class ShowSnackBar(val title:String, val message:String?):UIEvent
}